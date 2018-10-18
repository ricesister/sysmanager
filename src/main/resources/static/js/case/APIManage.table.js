$(function(){
	$("#ArbetTable").bootstrapTable({ // 对应table标签的id
	      url: "/getAPIList", // 获取表格数据的url
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
	              fproid: $("#fproid").val(),
	              faurl: $("#faurl").val(),
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
	              field: 'faid',
	              title: '接口编号',
	              align: 'center',
	              valign: 'middle',
	              visible: false
	            
	          }, {
	              field: 'fproid',
	              title: '项目编号',
	              align: 'center',
	              valign: 'middle',
	              visible: false
	            
	          }, 
	          {
	              field: 'fapiname',
	              title: '接口描述',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'faurl',
	              title: '请求地址',
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
	              field: 'fcasenum',
	              title: '用例数',
	              align: 'center',
	              valign: 'middle',
	          },
	          
	          {
	              field: 'fupdatetime',
	              title: '更新时间',
	              align: 'center',
	              valign: 'middle',
	            //获取日期列的值进行转换
	              formatter: function (value, row, index) {
	                  return changeDateFormat(value)
	              }
	          }, {
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
	var html = '<button class="btn btn-xs btn-info"  onclick="editAPI('+row.faid+')"><i class="icon-edit bigger-120"></i>编辑'
			+ '</button>&nbsp;<button class="btn btn-xs btn-danger" onclick="delAPI('+row.faid+')"><i class="icon-trash bigger-120"></i>删除</button>'
			+ '&nbsp;<button class="btn btn-xs btn-success"><i class="ddddicon-arrow-right icon-on-right"></i>执行</button>'
			+ '&nbsp;<button class="btn btn-xs btn-warning" onclick="toCase('+row.faid+','+row.fproid+')"><i class="icon-cog"></i>用例管理</button>';
	return html;
}

function toCase(aid,proid){
	 window.location.href="/toCaseManager?faid="+aid+"&fproid="+proid;
}

function changeCondition(){
	$("#ArbetTable").bootstrapTable('refresh');
}

//删除项目
function delAPI(aid){
	if(aid != null && aid != ""){
		layer.alert('确认删除？', {
		    skin: 'layui-layer-molv' //样式类名  自定义样式
		    ,closeBtn: 1    // 是否显示关闭按钮
		    ,anim: 1 //动画类型
		    ,btn: ['确定','取消'] //按钮
		    ,icon: 6    // icon
		    ,shade: 0
		    ,yes:function(){
		       // layer.msg('按钮1')
		    	$.get("/delAPI", 
		    			{faid:aid}, 
		    		function(data, status) {
		    			if (data.code == "10000") {
		                    layer.msg( '删除成功' );
		                    setTimeout(function(){
		                    	$("#ArbetTable").bootstrapTable('refresh');
		                    }, 500);
		    			} else {
		    				layer.msg(data.msg);
		    			}
		    		});
		    }
		    ,btn2:function(){
		      //  layer.msg('按钮2')
		    }});
		
	}
}


//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
	if(cellval == '' ||cellval == null){
		return;
	}
	
	var d = new Date(cellval);

	var times=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
	return times;
}

function editAPI(aid){
	layer.open({
	  type: 2,
	  shade: false,
	  closeBtn: 1,
	  title: "API编辑", //不显示标题
	  area : ['600px' , '600px'], 
      scrollbar: false,//禁止浏览器滚动
	  content:  '/toEditAPI?faid='+aid, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	});
}


function addAPI(){
	layer.open({
	  type: 2,
	  shade: false,
	  closeBtn: 1,
	  title: "API新增", //不显示标题
	  area : ['400px' , '600px'], 
      scrollbar: false,//禁止浏览器滚动
	  content:  '/toAddAPI', //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	});
}

/**
 * 前往下载exls模板页面
 */
function importApi(){
	layer.open({
	  type: 2,
	  shade: false,
	  closeBtn: 1,
	  title: "上传", //不显示标题
	  area : ['800px' , '400px'], 
      scrollbar: false,//禁止浏览器滚动
	  content:  '/toUploadApi', //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	});
}