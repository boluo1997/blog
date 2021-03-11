public static Column patchFilter(String patch, String... retain) {
		return udf((UDF1<?, ?>) (String p) -> {

			ArrayNode a = mapper.createArrayNode();
			String pathValueStr = "";

			JsonNode jsonNode = mapper.readTree(p);

			for (int i = 0; i < jsonNode.size(); i++) {
				Iterator<String> keys = jsonNode.get(i).fieldNames();
				while (keys.hasNext()) {
					if (jsonNode.get(i).findValue("op").asText().equals("replace")) {

						/*
						if (keys.next().equals("path")) {
							pathValueStr = jsonNode.get(i).findValue("path").asText();

							for (String tempStr : retain) {
								if ((pathValueStr).startsWith(tempStr, 0) && (tempStr.length() == (pathValueStr).length() || (pathValueStr).charAt(tempStr.length()) == '/')) {
									a.add(jsonNode.get(i));
								}
							}
						}
						*/

						//path路径
						String pathStr = jsonNode.get(i).findValue("path").asText();
						//拿到value
						ObjectNode o = (ObjectNode) jsonNode.at("/0/value");

						//路径不为空, 传入的字符串也会带上path
						if(pathStr.length() > 0){
							List<String> list = new LinkedList<>();

							//把带上path的那部分切掉
							for (String tempStr : retain) {
								if(tempStr.startsWith(pathStr)){
									list.add(tempStr.substring(pathStr.length()));
								}
							}

							String[] strArr = list.toArray(new String[0]);
							
							if(strArr.length > 0){
								recursionOp(o, strArr);

								a.addObject()
										.put("op", "replace")
										.put("path", jsonNode.get(i).path("path").asText())
										.putPOJO("value", o);

								return a.toString();
							}

						}

						recursionOp(o, retain);
						a.addObject()
								.put("op", "replace")
								.put("path", jsonNode.get(i).path("path").asText())
								.putPOJO("value", o);

						return a.toString();

					} else if (jsonNode.get(i).findValue("op").asText().equals("remove")) {
						if (keys.next().equals("path")) {
							pathValueStr = jsonNode.get(i).findValue("path").asText();

							for (int j = 0; j < retain.length; j++) {
								String tempStr = retain[j];

								String[] pathValueArr = pathValueStr.split("/");
								String[] tempStrArr = tempStr.split("/");

								//路径深度一致, 判断是否完全一致
								if (pathValueArr.length == tempStrArr.length && pathValueStr.equals(tempStr)) {
									a.add(jsonNode.get(i));
								} else if (pathValueArr.length > tempStrArr.length) {    //如果数据湖中的路径深度 > 传入需要判断的路径深度
									if (pathValueStr.startsWith(tempStr)) {
										a.add(jsonNode.get(i));
									}
								} else {    //如果数据湖中的路径深度 < 传入的需要比较的路径深度
									if (tempStr.startsWith(pathValueStr)) {
										a.add(jsonNode.get(i));
									}
								}
							}
						}
					} else if (jsonNode.get(i).path("op").asText().equals("add")) {

						if (jsonNode.at("/0/value").isObject()) {

							ObjectNode o = (ObjectNode) jsonNode.at("/0/value");
							recursionOp(o, retain);

							/*
							String[] strings = Arrays.stream(retain).map(tempStr -> {
								return tempStr.replace("/", "");
							}).toArray(String[]::new);
							//o.retain(strings);
							*/

							a.addObject()
									.put("op", "add")
									.put("path", jsonNode.get(i).path("path").asText())
									.putPOJO("value", o);

							return a.toString();
						}
						throw new UnsupportedOperationException("value非object对象");
					} else {
						throw new UnsupportedOperationException("非add, remove, replace操作");
					}

				}
			}

			return a.toString();
		}, StringType$.MODULE$).apply(expr(patch));
	}