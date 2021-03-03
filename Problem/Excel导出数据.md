~~~java
@GetMapping("excelGroup1.do")
    @ApiOperation("导出百姓群")
    @RequiresPermissions("sys:wechat:excelGroup1")
    public void download(HttpServletRequest request, HttpServletResponse response, @ApiParam  WechatGroupCond cond) throws Exception {
        cond.setCount(0);
        cond.setPageNo(0);
        //cond.setAreaId(ShiroUtils.getDefultAreaId(cond.getAreaId()));
        cond.fillDefaultUserProperty(ShiroUtils.getUserEntity());
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(getTitleName(cond), "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        TokenUser user = ShiroUtils.getUserEntity();
        cond.setUserAreaId(user.getAreaId().longValue());
        cond.setUserId(user.getUserId());

        //获得用户
        List<WechatGroupListModel> data = wechatGroupInfoService.getWechatGroup1List(cond).getData();
        ExcelWriter writer = ExcelUtil.getWriter();
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(data)) {
            for (WechatGroupListModel model : data) {
                Map<String, Object> row = new HashMap<>();
                row.put("微信群名称",model.getGroupName());
                row.put("群类型",model.getGroupType() == 1 ? "主群" : (model.getGroupType() == 2 ? "统筹群" : "兴趣群"));
                row.put("所属区域",model.getAreaName());
                row.put("本群覆盖户数",model.getTotalHomeCount());
                row.put("本村群总覆盖户数",model.getAreaTotalHomeCount());
                row.put("村总户数",model.getHomeCount());
                row.put("入群人数",model.getTotalUserCount());
                row.put("更新时间",model.getLastUpdate());
                rows.add(row);
            }
        }
        writer.write(rows, true);
        writer.flush(response.getOutputStream());
        writer.close();

    }
~~~

