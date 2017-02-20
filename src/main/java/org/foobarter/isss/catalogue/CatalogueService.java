package org.foobarter.isss.catalogue;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class CatalogueService {

	private CatalogEntry[] entries = {
			new CatalogEntry(0, null, "Plush Toys", null, true),
			new CatalogEntry(1, null, "Instruments", null, true),
			new CatalogEntry(2, null, "Family Fun", null, true),

			new CatalogEntry(3, 1L, "BoJack Horseman", new BigDecimal("19.99"), false),
			new CatalogEntry(4, 2L, "Princess Carolyn", new BigDecimal("29.99"), false),
			new CatalogEntry(5, 3L, "Diane Nguyen", new BigDecimal("29.99"), false),

			new CatalogEntry(6, 4L, "Pan Flute", new BigDecimal("199.99"), false),
			new CatalogEntry(7, 5L, "Panini Flute", new BigDecimal("99.99"), false),

			new CatalogEntry(8, 6L, "Portal Gun", new BigDecimal("999.99"), false),
	};

	private int[] roots = {0, 1, 2};

	private int[][] lists = {
			{3, 4, 5},
			{6, 7},
			{8},

			{},
			{},
			{},

			{},
			{},

			{}
	};

	public List<CatalogEntry> root() {
		return list(null);
	}

	public List<CatalogEntry> list(String parentString) {
		List<CatalogEntry> ret = new LinkedList<>();
		if (parentString != null) {
			CatalogEntry parent = entries[Integer.parseInt(parentString)];
			for (int i : lists[(int)parent.getId()]) {
				ret.add(entries[i]);
			}
		}
		else {
			for (int i : roots) {
				ret.add(entries[i]);
			}
		}

		return ret;
	}
}
