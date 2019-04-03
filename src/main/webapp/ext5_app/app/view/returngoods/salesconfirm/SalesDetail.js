
Ext.define('BPSPortal.view.returngoods.salesconfirm.SalesDetail', {
    extend: 'Ext.form.Panel',
    alias: 'widget.salesdetail',

    requires: [
		'BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewModel',
        'BPSPortal.view.returngoods.salesconfirm.SalesConfirmViewController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.toolbar.Fill',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.view.Table',
        'Ext.selection.CheckboxModel',
        'Ext.grid.column.Widget'
    ],

    viewModel: {
        type: 'salesconfirm'
    },
    controller: 'salesconfirm',
    bodyPadding: 10,
    closable: true,
    scrollable: true,
    title: '销售明细确认',
    listeners: {
  	  afterrender: 'onAfterRenders'
    },
    items:[
           {
               xtype: 'gridpanel',
               reference:'uploadresaleGrid',
               bind:{
            	   store: '{uploadresale}'
               },
               height:500,
               viewConfig:{
               	  enableTextSelection:true
               },
               plugins: [
                         {
                             ptype: 'cellediting',
                             clicksToEdit:1
                         }
                       ],
               columns: [
         				{
        				    xtype: 'gridcolumn',
        				    dataIndex: 'errormsg',
        				    width:300,
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
                            width:200,
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
        					text : '  序列号'
//                            editor: {
//                                xtype: 'textfield',
//                                name: 'seqno'
//                            }
        				}, 
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'custname',
                            width:300,
                            text: '订货单位'
//                            editor: {
//                                xtype: 'textfield',
//                                name: 'custname'
//                            }
                        },
                        {
        					xtype : 'gridcolumn',
        					dataIndex : 'materialname',
        					width:150,
        					text : '产品名称'

        				}, 
        				{
        					xtype : 'gridcolumn',
        					dataIndex : 'materialcode',
        					text : ' 产品编码'
        				}, 
                        {
                            xtype: 'gridcolumn',
                            width:150,
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
                            text: '付款方式'

                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'currency',
                            text: '货币'
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
                            format:'0,000.00',
                            align:'right',
                            text: '未税合同额'

                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'isspl',
                            text: '特价',

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
                            width:300,
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
                            format:'0,000.0000',
                            width:300,
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
                            width:300,
                            align:'right',
                            text: '经销商标准采购额（未税）'
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'dealerstdact',
                            width:300,
                            format:'0,000.0000',
                            align:'right',
                            text: '经销商实际采购额（未税）'
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'rebateamount',
                            format:'0,000.00',
                            width:300,
                            align:'right',
                            text: '  返货金额（未税）'
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'bpsstdcost',
                            format:'0,000.0000',
                            width:300,
                            align:'right',
                            text: '  BPS标准成本价（未税）',
                            editor: {
                            	xtype : 'numberfield',
        						decimalPrecision:4,  
        						allowDecimals:true,
                                name: 'bpsstdcost',
                            	listeners: {
                                    blur:function(value){
                                 	   var count=value.getValue();
                                 	   if((count!=null&&count!='') || count==0){
                                 		   var grid=Ext.ComponentQuery.query("viewport salesdetail grid")[0];
                                     	   var record = grid.getSelectionModel().getSelection()[0];
                                     	   var qty=record.data.qty;//数量
                                     	   var bpssplcost=record.data.bpssplcost;//BPS特价成本价（未税）
                                     	   
                                     	   
                                     	  var dealerstdpo=record.data.dealerstdpo;//经销商标准采购额
                                     	  var dealerstdact=record.data.dealerstdact;//经销商实际采购额
                                     	  if(bpssplcost>count){
                                     		  record.set('rebateamount',0);
                                     	   }else{
                                     		  record.set('rebateamount',dealerstdpo-dealerstdact);
                                     	   }
                                     	  var rebateamount=record.data.rebateamount;//返货金额
                                     	  
                                     	  
                                     	   
                                     	   var num=(count*qty-bpssplcost*qty)-rebateamount
                                     	   record.set('rebatediff',num.toFixed(2));
                                 	   }
                                 	  
                                    }
                                }
                             }
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'bpssplcost',
                            format:'0,000.0000',
                            width:300,
                            align:'right',
                            text: '  BPS特价成本价（未税）',
                            editor: {
                            	xtype : 'numberfield',
        						decimalPrecision:4,  
        						allowDecimals:true,
                                name: 'bpssplcost',
                               	listeners: {
                                    blur:function(value){
                                 	   var count=value.getValue();
                                 	   if((count!=null&&count!='') || count==0){
                                 		   var grid=Ext.ComponentQuery.query("viewport salesdetail grid")[0];
                                     	   var record = grid.getSelectionModel().getSelection()[0];
                                     	   var qty=record.data.qty;//数量
                                     	//   var rebateamount=record.data.rebateamount;//返货金额
                                     	   var bpsstdcost=record.data.bpsstdcost;//BPS特价成本价（未税）
                                     	   
                                     	  var dealerstdpo=record.data.dealerstdpo;//经销商标准采购额
                                     	  var dealerstdact=record.data.dealerstdact;//经销商实际采购额
                                     	  if(count>bpsstdcost){
                                     		  record.set('rebateamount',0);
                                     	   }else{
                                     		  record.set('rebateamount',dealerstdpo-dealerstdact);
                                     	   }
                                     	   var rebateamount=record.data.rebateamount;//返货金额
                                     	   var num=(bpsstdcost*qty-count*qty)-rebateamount
                                     	   record.set('rebatediff',num.toFixed(2));
                                 	   }
                                 	  
                                    }
                                }
                             }
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
                            width:200,
                            text: '备注',
                            editor: {
                                xtype: 'textfield',
                                name: 'remark'
                             }
                        }
                    ],
               dockedItems: [
                   {
                       xtype: 'toolbar',
                       dock: 'top',
                       items: [
                           {
                               xtype: 'button',
                               width: 100,
                               text: '确定',
                               listeners: {
                                   click: 'onsubmitClick'
                               }
                           }
                       ]
                   }
               ]
           }
        ]


});