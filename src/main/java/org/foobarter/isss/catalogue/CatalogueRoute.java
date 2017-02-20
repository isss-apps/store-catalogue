package org.foobarter.isss.catalogue;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.boot.FatJarRouter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogueRoute extends FatJarRouter {

	// must have a main method spring-boot can run
	public static void main(String[] args) {
		FatJarRouter.main(args);
	}

	@Autowired
	private CatalogueService catalogueService;

    @Override
    public void configure() throws Exception {
		restConfiguration().component("netty4-http").host("0.0.0.0").port(8080).bindingMode(RestBindingMode.auto);

		rest("/categories")
				.consumes("application/json").produces("application/json")
				.get("/list").outTypeList(CatalogEntry.class)
					.to("bean:catalogueService?method=root")
				.get("/list/{id}").outTypeList(CatalogEntry.class)
					.to("bean:catalogueService?method=list(${header.id})");
	}
}