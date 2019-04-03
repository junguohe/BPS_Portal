

Ext.define('BPSPortal.view.main.MainView', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.mainview',

    requires: [
        'BPSPortal.view.main.MainViewViewModel',
        'BPSPortal.view.main.MainViewViewController',
        'Ext.tab.Panel',
        'Ext.tab.Tab'
    ],

    viewModel: {
        type: 'mainview'
    },
    itemId: 'mainView',
    layout: 'border',
    controller:'mainview',
    items: [
        {
            xtype: 'panel',
            region: 'north',
            height: 100,
            html:
            	'<div class="navbar-container" id="navbar-container">'
            	+'	<div style="margin:15px;">'
            	//+'		<img src="images/philips.png">'
            	+'	</div>'
                +'<div style="width:450px;height: 71px">'
                +'<img src="images/logo.png" style="width: 190px;height: 71px;"><span style="font-size: 28px;margin-left: 20px;">经销商管理系统</span>'
                +'</div>'
            	+'	<div class="headerright" onmouseover="displaySubMenu(this);" onmouseout="hideSubMenu(this);">'
            	+'		<div class="headercoursor">'
            	+'			<img src="images/user.png">'
            	+'			<span>'+currentUserName+'</span>'
            	+'		</div>'
            	+'		<ul class="subMenu"> '
            	+'			<li class="modifypwd"><a href="#" onclick="onUpdateClick()">修改密码</a></li> '
            	+'			<li class="logout"><a href="resources/j_spring_security_logout">退出登录</a></li>'
            	+'		</ul>'
            	+'	</div>'
            	+'</div>'
 
        },
        {
        	xtype: 'menupanel',
            region: 'west',
            split: true
        },
        {
            xtype: 'tabpanel',
            flex: 1,
            region: 'center',
            reference:'main',
            itemId: 'contentPanel'
        }
    ]

});

function onUpdateClick(){
	var passWordPanel = Ext.create('BPSPortal.view.user.PassWordWin');
	passWordPanel.show();
}