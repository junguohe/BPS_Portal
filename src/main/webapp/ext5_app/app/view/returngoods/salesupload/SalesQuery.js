
Ext.define('BPSPortal.view.returngoods.salesupload.SalesQuery', {
    extend: 'Ext.form.Panel',
    alias: 'widget.salesquery',

    requires: [
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewModel',
        'BPSPortal.view.returngoods.salesupload.SalesUploadViewController',
        'Ext.form.Panel',
        'Ext.form.Label',
        'Ext.button.Button',
        'Ext.form.field.File',
        'Ext.form.field.Date',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.grid.Panel',
        'Ext.grid.column.Column',
        'Ext.view.Table'
    ],

    viewModel: {
        type: 'salesupload'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
    	  afterrender: 'onAfterRenders'
      },
    controller : 'salesupload',
    title: '销售数据',
    closable: true,
    scrollable: true,
    items: [
        {
            xtype: 'gridpanel',
            height: 500,
            title: '',
            reference:'uploadresalequerygrid',
            bind:{
            	store: '{uploadresalequery}'
            },
            viewConfig:{
            	enableTextSelection:true
            },
            columns: [
				{
				    xtype: 'gridcolumn',
				    dataIndex: 'errormsg',
				    width:200,
				    text: 'ErrorMsg'
				},
				{
				    xtype: 'gridcolumn',
				    dataIndex: 'id',
				    hidden:true,
				    text: 'id'
				},
				{
                    xtype: 'gridcolumn',
                    hidden:true,
                    dataIndex: 'tid',
                    text: 'tid'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'region',
                    text: '区域'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'dealername',
                    text: '经销商'
                  },
                {
					xtype : 'gridcolumn',
					dataIndex : 'period',
					text : '会计周期'
				}, 
				{
					xtype : 'gridcolumn',
					dataIndex : 'seqno',
					width:200,
					text : '  序列号',
                    editor: {
                        xtype: 'textfield',
                        name: 'seqno'
                    }
				}, 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'custname',
                    width:200,
                    text: '订货单位'
                },
                {
					xtype : 'gridcolumn',
					dataIndex : 'materialname',
					width:200,
					text : '产品名称'
				}, 
				{
					xtype : 'gridcolumn',
					dataIndex : 'materialcode',
					width:150,
					text : ' 产品编码'
				}, 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'batchno',
                    text: '批次号'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'qty',
                    format:'0,000', 
                    align:'right',
                    text: ' 出货数量'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'paymenttype',
                    width:200,
                    text: '付款方式'
                },
            	{
					xtype : 'gridcolumn',
					dataIndex : 'currency',
					text : '币种'
				}, 
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'deliverydate',
                    text: '交货日期',
                   renderer: function (val) {
                        var dt = new Date(val);
                        var update_date = Ext.Date.format(dt, 'Y-m-d')
                        if (val == "") {
                            return "";
                        }          
                        return update_date;
                   
                   }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'unitprice',
                    format:'0,000.000', 
                    align:'right',
                    text: ' 单价'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'istax',
                    text: '含税否',
                    renderer : function(value) {
                    	var result='';
                    	if(value=='1'){
                    		result='是'
                    	}else{
                    		result='否';
                    	}
                    	return result;
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'contractamount',
                    format:'0,000.0000',
                    align:'right',
                    text: '未税合同额'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'isspl',
                    text: '  是否特价',
                     renderer : function(value) {
                     	var result='';
                     	if(value=='1'){
                     		result='是'
                     	}else{
                     		result='否';
                     	}
                     	return result;
                     }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdcost',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '  经销商标准成本价（未税）',
                    renderer: function (value, metaData, record) {
                 	    var updated = record.get('errormsg');
                 	    var text='|经销商标准成本价不匹配';
                       if (updated.indexOf(text)!=-1){ 
                       		return '<span style="color:' + "red" + ';">' + Ext.util.Format.number(value,'0,000.0000')  + '</span>';
                       }else {
                       		return  Ext.util.Format.number(value,'0,000.0000');
                      }
                 	}
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealersplcost',
                    text: '  经销商特价成本价（未税）',
                    width:200,
                    format:'0,000.0000',
                    align:'right',
                    renderer: function (value, metaData, record) {
                 	    var updated = record.get('errormsg');
                 	    var text='|经销商特价成本价不匹配';
                       if (updated.indexOf(text)!=-1){ 
                       		return '<span style="color:' + "red" + ';">' + Ext.util.Format.number(value,'0,000.0000')  + '</span>';
                       }else {
                       		return  Ext.util.Format.number(value,'0,000.0000');
                      }
                 	}
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdpo',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '经销商标准采购额（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'dealerstdact',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '经销商实际采购额（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'rebateamount',
                    format:'0,000.00',
                    align:'right',
                    width:200,
                    text: '  返货金额（未税）'

                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'bpsstdcost',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '  BPS标准成本价（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'bpssplcost',
                    format:'0,000.0000',
                    align:'right',
                    width:200,
                    text: '  BPS特价成本价（未税）'
                },
                {
                    xtype: 'numbercolumn',
                    format:'0,000.00',
                    dataIndex: 'rebatediff',
                    text: '  返货差异（未税）',
                    width:200,
                    align:'right'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'remark',
                    width:150,
                    text: '备注'
                }
            ],
            dockedItems: [
          				{
          				    xtype: 'pagingtoolbar',
          				    dock: 'bottom',
          				    bind: {
          				        store: '{uploadresalequery}'
          				    },
          				    displayInfo: true
          				}
          	        ]
        }
    ]

});