$(function(){
	$("#ArbetTable").bootstrapTable({ // 对应table标签的id
	      url: "/getCaseList", // 获取表格数据的url
	      cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
	      striped: true,  //表格显示条纹，默认为false
	      pagination: true, // 在表格底部显示分页组件，默认false
	      pageList: [5, 10, 15], // 设置页面可以显示的数据条数
	      pageSize: 5, // 页面数据条数
	      pageNumber: 1, // 首页页码
	      sidePagination: 'server', // 设置为服务器端分页
	      queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求

	          return {
	              pageSize: params.limit, // 每页要显示的数据条数
	              offset: params.offset, // 每页显示数据的开始行号
	              sort: params.sort, // 要排序的字段
	              sortOrder: params.order, // 排序规则
	              faid: $("#faid").text()
	          }
	      },
	      sortName: 'frowid', // 要排序的字段
	      sortOrder: 'desc', // 排序规则
	      columns: [
	          {
	              checkbox: true, // 显示一个勾选框
	              align: 'center' // 居中显示
	          }, {
	              field: 'frowid', // 返回json数据中的name
	              title: '序号', // 表格表头显示文字
	              align: 'center', // 左右居中
	              valign: 'middle' // 上下居中
	          }, {
	              field: 'fcasedesp',
	              title: '用例描述',
	              align: 'center',
	              valign: 'middle'
	            
	          }, {
	              field: 'fcaseid',
	              title: '用例描述',
	              align: 'center',
	              valign: 'middle',
	              visible: false
	            
	          }, 
	          {
	              field: 'frequest',
	              title: '请求参数',
	              align: 'center',
	              valign: 'middle',
	              cellStyle:{ 
	            	  css:{ 
	            	  "overflow": "hidden", 
	            	  "text-overflow": "ellipsis", 
	            	  "white-space": "nowrap" 
	            	  }
            	  } 
	          }, {
	              field: 'fexpect',
	              title: '预期结果',
	              align: 'center',
	              valign: 'middle',
	              cellStyle:{ 
	            	  css:{ 
	            	  "overflow": "hidden", 
	            	  "text-overflow": "ellipsis", 
	            	  "white-space": "nowrap" 
	            	  }
            	  } 
	          },{
	              field: 'fupdatetime',
	              title: '更新时间',
	              align: 'center',
	              valign: 'middle'
	          },{
	              title: "操作",
	              align: 'center',
	              valign: 'middle',
	              width: 300, // 定义列的宽度，单位为像素px
	              formatter: operation
	          }
	      ],
	      onLoadSuccess: function(){  //加载成功时执行
	            console.info("加载成功");
	      },
	      onLoadError: function(){  //加载失败时执行
	            console.info("加载数据失败");
	      }

	});
	
});

function operation(value, row, index){
	var html = '<button class="btn btn-xs btn-info" onclick="toEditCase('+row.fcaseid+','+$("#faid").text()+','+$("#fproid").val()+',\''+$("#url").text()+'\')"><i class="icon-edit bigger-120"></i>编辑'
			+ '</button>&nbsp;<button class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i>删除</button>'
			+ '&nbsp;<button class="btn btn-xs btn-success"><i class="icon-arrow-right icon-on-right"></i>执行</button>'
			+ '&nbsp;<button class="btn btn-xs btn-warning"><i class="icon-cog"></i>执行日志</button>';
	return html;
}


function changeCondition(){
	$("#ArbetTable").bootstrapTable('refresh');
}

function toEditCase(caseid,aid,proid,furl){
	window.location.href="/getCaseInfo?fcaseid="+caseid+"&faid="+aid+"&fproid="+proid+"&furl="+furl;
}
