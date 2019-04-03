
Ext.define('BPSPortal.view.systemadministration.prodperson.ProdPersonViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.prodperson',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	 mgrstore:{
      	   model: 'BPSPortal.model.Person',
  	       autoLoad: true,
  	       listeners:{
	       	  load:function(store,records,successful,eOpts){
	     	   		if(store.getCount()<0){
	   	   			return;
	   	   		}
	       		for (var i = 0; i < store.getCount(); i++) {
	       			 var rec = 	store.getAt(i);
	       		     var empid =store.getAt(i).data.empid;
	       		     var per_name=store.getAt(i).data.per_name;
	       		     var text=empid+'/'+per_name;
	       		     rec.set('s',text);
	       		     
	       		     
	               }
	       	  }
  	       },
  	       proxy: {
                 type: 'direct',
                 api: {
                	 //不分页
              	  read: personControl.getAll
                 },
                 reader: {
                     totalProperty: 'total',
                     rootProperty: 'records'
                 }
             }
         },
    	prodperson: {
	       model: 'BPSPortal.model.Person',
	       pageSize:15,
	       autoLoad: true,
	       proxy: {
               type: 'direct',
               api: {
            	  read: prodpersonControl.getAll
               },
               reader: {
                   totalProperty: 'total',
                   rootProperty: 'records'
               }
           }
	   },
	   area:{
    	   model: 'BPSPortal.model.Dictionary',
            autoLoad: true,
            proxy: {
                type: 'direct',
                api: {
             	  read: directoryController.findList,
                },
            extraParams:{
                 	module:'customer',
                 	functions:'area'
                },
                reader: {
                    totalProperty: 'total',
                    rootProperty: 'records'
                }
            }
       }

    }
});