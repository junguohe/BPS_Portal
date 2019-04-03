
Ext.define('BPSPortal.view.help.PersonPicker', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.personpicker', // 这个组件是选择产品的组件 
    emptyText: '',
    editable: false,
    matchFieldWidth: true,

    store: Ext.create('Ext.data.Store', {
        model: 'BPSPortal.model.Person',
        proxy: {
            type: 'direct',
            api: {
            	read: personControl.getAll
    	    },
    	    extraParams:{
    	    	fempgroup:'FAE'
                },
            reader: {
                type: 'json',
                rootProperty: 'records'
            }
        }

    }),
    listConfig: {
        itemTpl: [
                  '<div data-qtip="per_name:{per_name}">{per_name}</div>'
                ]
    },

    typeAhead: true,
    minChars: 1,
    queryMode: 'remote',
    queryParam: 'name',
    typeAhead: true,
    editable: true,
    queryDelay : 700
});

