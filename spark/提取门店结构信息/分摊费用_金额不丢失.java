
public static Column 分摊(String startDateExpr, String endDateExpr, String amountExpr) {
		
		return udf((Date start, Date end, Long amount) -> {

			List<Row> result = Lists.newArrayList();
			long countDays = start.toLocalDate().until(end.toLocalDate(), ChronoUnit.DAYS);

			long surplus = amount % countDays;
			long avg = amount / countDays;

			//比如92元/30天, 会余2, 把剩下的2平分到倒数第一天和倒数第二天
			//这里分两段, 前28天, 每天都是3元钱, 29 30天是(3+1)元
			for (LocalDate startDay = start.toLocalDate(); startDay.compareTo(end.toLocalDate().minusDays(surplus)) < 0; startDay = startDay.plusDays(1)) {
				result.add(RowFactory.create(Date.valueOf(startDay), avg));
			}
			for (LocalDate startDay = end.toLocalDate().minusDays(surplus); startDay.compareTo(end.toLocalDate()) < 0; startDay = startDay.plusDays(1)) {
				result.add(RowFactory.create(Date.valueOf(startDay), avg + 1));
			}

			return result;
		}, ArrayType.apply(new StructType()
				.add("date", "date")
				.add("amount", "long")))
				.apply(expr(startDateExpr), expr(endDateExpr), expr(amountExpr));
}