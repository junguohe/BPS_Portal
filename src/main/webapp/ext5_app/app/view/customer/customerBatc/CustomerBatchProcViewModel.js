Ext.define('BPSPortal.view.customer.customerBatc.CustomerBatchProcViewModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.customerbatchproc',
	requires : [],

	stores : {
		CustomerBatch : {
			model : 'BPSPortal.model.CustomerBatch',
			proxy : {
				type : 'direct',
				api : {
					read : customerControl.readerCustomerBatch,
				},
				reader : {
					type: 'json',
					totalProperty : 'total',
					rootProperty : 'records'
				}
			}
		},
		type : {
			fields : [ 'id', 'type' ],
			data : [ [ '0', 'Open' ], [ '1', '延期' ] ]
		}
	}

});