
Ext.define('BPSPortal.view.help.MaterialPickerViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.materialpicker',
    
    onMaterialSelected: function(thisGrid, record){
    	this.getView().setValue(record.data.id);
    },
	 onCustSelected: function(thisGrid, record){
		this.getView().setValue(record.data.cpid);
	},
	onProjSelected: function(thisGrid, record){
		this.getView().setValue(record.data.projname);
	},
    
});
