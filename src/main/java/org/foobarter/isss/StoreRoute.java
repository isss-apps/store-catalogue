package org.foobarter.isss;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;

import org.foobarter.isss.catalogue.CatalogEntry;
import org.foobarter.isss.catalogue.CatalogueService;
import org.foobarter.isss.order.Order;
import org.foobarter.isss.order.OrderProcessor;
import org.foobarter.isss.order.OrderReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreRoute extends RouteBuilder {
	// must have a main method spring-boot can run
	public static void main(String[] args) {
		SpringApplication.run(StoreRoute.class, args);
	}

	@Autowired
	private CatalogueService catalogueService;

	@Autowired
	private OrderProcessor orderProcessor;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("netty4-http").host("0.0.0.0").port(8080).bindingMode(RestBindingMode.auto);

		rest("/entries")
				.consumes("application/json").produces("application/json")
					.get("/{id}").outType(CatalogEntry.class)
						.route()
							.choice() // simulated infinite cycle
								.when(header("id").isEqualTo("9"))
									.to("direct:timeout")
								.endChoice()
							.otherwise()
								.to("bean:catalogueService?method=entry(${header.id})")
					.endRest()
					.get("/list").outTypeList(CatalogEntry.class)
						.to("bean:catalogueService?method=root")
							.get("/list/{id}").outTypeList(CatalogEntry.class)
								.route()
									.choice() // simulated infinite cycle
										.when(header("id").isEqualTo("9"))
											.to("direct:timeout")
										.endChoice()
									.otherwise()
										.to("bean:catalogueService?method=list(${header.id})")
					.endRest();

		rest("/order")
				.consumes("application/json").produces("application/json")

				.put().type(Order.class).outType(OrderReceipt.class)
					.route()
						.log("Body: ${body}")
						.choice() // simulated infinite delay
							.when(body().method("getCustomer").method("getName").isEqualToIgnoreCase("error"))
								.to("direct:timeout")
							.endChoice()
							.otherwise()
								.idempotentConsumer(simple("${body.uuid}"),
									MemoryIdempotentRepository.memoryIdempotentRepository(1024))
								.skipDuplicate(false)
								.log("Processing order ${body.uuid} for ${body.customer.name}, ${body.customer.address}")
								.process(orderProcessor)
								.log("Receipt for ${body.price}: ${body.message}");

		from("direct:timeout")
				.delay(5 * 60_000).asyncDelayed()
				.setHeader("CamelHttpResponseCode", simple("503"));
	}
}
