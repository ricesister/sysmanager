$(function(){
	$("#ArbetTable").bootstrapTable({ // 对应table标签的id
	      url: "/getUserList", // 获取表格数据的url
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
	              username: $("#username").val(),
	              phone: $("#phone").val(),
	              effect: $("#effect").val()
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
	              field: 'fuserid',
	              title: '账户id',
	              align: 'center',
	              valign: 'middle',
	              visible: false
	            
	          }, 
	          {
	              field: 'fname',
	              title: '账户名称',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'fphone',
	              title: '手机号',
	              align: 'center',
	              valign: 'middle'
	          },{
	              field: 'femail',
	              title: 'Email',
	              align: 'center',
	              valign: 'middle'
	          },{
	              field: 'fpassword',
	              title: '密码',
	              align: 'center',
	              valign: 'middle',
            	  visible: false
	          },{
	              field: 'fcreatetime',
	              title: '创建时间',
	              align: 'center',
	              valign: 'middle',
	            //获取日期列的值进行转换
	              formatter: function (value, row, index) {
	                  return changeDateFormat(value)
	              }
	          }, {
	              field: 'feffect',
	              title: '状态',
	              align: 'center',
	              valign: 'middle',
	              formatter: feffectDiv
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

function feffectDiv(value, row, index){
	var html = '';
	if(value == 1){
		html = '<span class="label label-sm label-success">正常</span>';
	}else{
		html = '<span class="label label-sm label-warning">已锁定</span>';
	}
	return html;
}

function operation(value, row, index){
	var html = '<button class="btn btn-xs btn-info" onclick="editUser(\''+row.fuserid+'\')"><i class="icon-edit bigger-120"></i>编辑'
			+ '</button>&nbsp;<button class="btn btn-xs btn-danger" onclick="delUser(\''+row.fuserid+'\')"><i class="icon-trash bigger-120"></i>删除</button>';
	if(row.feffect ==1){
		html = html + '&nbsp;<button class="btn btn-xs btn-warning" onclick="changeUserStatus(\''+row.fuserid+'\',\''+row.feffect+'\')"><i class="icon-flag bigger-120"></i>锁定</button>';
	}else{
		html = html + '&nbsp;<button class="btn btn-xs btn-success" onclick="changeUserStatus(\''+row.fuserid+'\',\''+row.feffect+'\')"><i class="icon-ok bigger-120"></i>取消锁定</button>';
	}
	return html;
}


function changeCondition(){
	$("#ArbetTable").bootstrapTable('refresh');
}

//删除用户
function delUser(userid){
	if(userid == '64c73369-4941-4f1e-bf2c-af05a810d696'){
		alert("无法操作管理员账号！");
		return;
	}
	if(userid != null && userid != ""){
		$.get("/delUserById", 
			{fuserid:userid}, 
		function(data, status) {
			if (data.code == "10000") {
                alert( '删除成功' );
                setTimeout(function(){
                	$("#ArbetTable").bootstrapTable('refresh');
                }, 500);
			} else {
				/*$("#message").text(data.msg);
				$("#message").css("color","red");*/
                alert('删除失败');
			}
		});
	}
}


//变更用户状态
function changeUserStatus(userid,effect){
	if(effect == 1){
		$.get("/changeUStatus", 
				{feffect:"0",fuserid:userid}, 
			function(data, status) {
				if (data.code == "10000") {
	                setTimeout(function(){
	                	//alert('操作成功');
	                	$("#ArbetTable").bootstrapTable('refresh');
	                }, 500);
				} else {
					alert('操作失败');
				}
		});
	}
	if(effect == 0){
		$.get("/changeUStatus", 
				{feffect:"1",fuserid:userid}, 
			function(data, status) {
				if (data.code == "10000") {
	                setTimeout(function(){
	                	$("#ArbetTable").bootstrapTable('refresh');
	                }, 500);
				} else {
					alert('操作失败');
				}
		});
	}
}

//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
	
	var d = new Date(cellval);

	var times=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
	return times;
}

/**
 * 修改用户模态框
 * @param userid
 * @returns
 */
function editUser(userid){
	layer.config({
		  skin: 'demo-class'
		})
	layer.open({
	  type: 2,
	  shade: false,
	  closeBtn: 1,
	  title: "用户编辑", //不显示标题
	  area : ['300px' , '500px'], 
      scrollbar: false,//禁止浏览器滚动
	  content:  '/getUserInfoById?fuserid='+userid, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	});
}


function addUser(userid){
	layer.config({
		  skin: 'demo-class'
		})
	layer.open({
	  type: 2,
	  shade: false,
	  closeBtn: 1,
	  title: "用户新增", //不显示标题
	  area : ['400px' , '600px'], 
      scrollbar: false,//禁止浏览器滚动
	  content:  'showAdd', //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
	});
}