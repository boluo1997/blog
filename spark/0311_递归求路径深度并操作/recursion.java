private static void recursionOp(ObjectNode obj, String... retainStrArr) {

		Map<String, String[]> p = new HashMap<>();
		String[] strArr;

		for (String retainStr : retainStrArr) {
			if (retainStr.length() == 0) {
				return;
			}
			int indexChar = retainStr.indexOf("/", 1);
			if (indexChar == -1) {
				p.put(retainStr, null);
			} else {
				List<String> listStr = new ArrayList<>();
				String preStr = retainStr.substring(0, indexChar);
				String aftStr = retainStr.substring(indexChar);
				listStr.add(aftStr);
				strArr = listStr.toArray(new String[0]);
				p.put(preStr, strArr);
			}
		}

		p.forEach((k, v) -> {
			if (!obj.at(k).isObject() || v == null) {
				return;
			}
			recursionOp((ObjectNode) obj.at(k), v);
		});

		String tempStr = "";
		List<String> tempList = p.keySet().stream().map(i -> {
			return i.substring(1);
		}).collect(Collectors.toList());

		obj.retain(tempList);

	}