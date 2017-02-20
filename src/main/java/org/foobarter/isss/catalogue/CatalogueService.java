package org.foobarter.isss.catalogue;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class CatalogueService {

	private CatalogEntry[] entries = {
			new CatalogEntry(0, null, "Plush Toys", null, true, 0L),
			new CatalogEntry(1, null, "Instruments", null, true, 1L),
			new CatalogEntry(2, null, "Family Fun", null, true, 2L),

			new CatalogEntry(3, 1000L, "BoJack Horseman", new BigDecimal("19.99"), false, 0L),
			new CatalogEntry(4, 1001L, "Princess Carolyn", new BigDecimal("29.99"), false, 0L),
			new CatalogEntry(5, 1002L, "Diane Nguyen", new BigDecimal("29.99"), false, 0L),

			new CatalogEntry(6, 1003L, "Pan Flute", new BigDecimal("199.99"), false, 1L),
			new CatalogEntry(7, 1004L, "Panini Flute", new BigDecimal("99.99"), false, 1L),

			new CatalogEntry(8, 1005L, "Portal Gun", new BigDecimal("999.99"), false, 2L),

			new CatalogEntry(9, 7L, "Nothing", new BigDecimal("999.99"), false, null),
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
				ret.add(entry(i));
			}
		}
		else {
			for (int i : roots) {
				ret.add(entry(i));
			}
		}

		return ret;
	}

	public CatalogEntry entry(int id) {
		CatalogEntry ret = entries[id];

		// Real infinite cycle, if you prefer to the simulated one:
		/*
		// compute root category
		if (ret.getRootCategory() == null) {
			// compute parent ids
			long root = id;
			found: while(true) {
				// is parent a root category already?
				for (int i : roots) {
					if (root == i) {
						break found;
					}
				}

				cont: for (int i = 0; i < lists.length; ++i) {
					for (int j : lists[i]) {
						if (j == root) {
							root = i;
							break cont;
						}
					}
				}
			}

			ret.setRootCategory(root);
		}*/



		return ret;
	}

	public CatalogEntry entry(String idString) {
		return entry(Integer.parseInt(idString));
	}
}
