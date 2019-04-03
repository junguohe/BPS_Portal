
Ext.define('BPSPortal.view.systemadministration.person.PersonViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.person',
	requires: [
	           'Ext.data.Store'
	       ],
    stores: {
    	person: {
	       model: 'BPSPortal.model.Person',
	       pageSize:15,
	       autoLoad: true,
	       proxy: {
               type: 'direct',
               api: {
            	  read: personControl.getAlls
               },
               reader: {
                   totalProperty: 'total',
                   rootProperty: 'records'
               }
           }
	   },
	   persons: {
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
	        		     rec.set('x',text);
	        		     
	        		     
	                }
	        	  }
	           },
	       proxy: {
               type: 'direct',
               api: {
            	  read: personControl.getAll
               },
               reader: {
                   totalProperty: 'total',
                   rootProperty: 'records'
               }
           }
	   },
	   area:{
		   model: 'BPSPortal.model.Person',
	       autoLoad: true,

           listeners:{
        	  load:function(store,records,successful,eOpts){
      	   		if(store.getCount()<0){
    	   			return;
    	   		}
        		for (var i = 0; i < store.getCount(); i++) {
        			 var rec = 	store.getAt(i);
        		     var area_id =store.getAt(i).data.area_id;
        		     var area=store.getAt(i).data.area;
        		     var text=area_id+'/'+area;
        		     rec.set('s',text);
        		     
        		     
                }
        	  }
           },
	       proxy: {
               type: 'direct',
               api: {
            	  read: prodpersonControl.getAlls
               },
               reader: {
                   totalProperty: 'total',
                   rootProperty: 'records'
               }
           }
	   },
	   salesfae:{
		   fields:['id','name'],
 		  data:[['FAE','FAE'],['sales','sales']]

	   },
	   noOrYes:{
		   model: 'BPSPortal.model.Dictionary',
            autoLoad: true,
            proxy: {
                type: 'direct',
                api: {
             	  read: directoryController.findList,
                },
            extraParams:{
                 	module:'all',
                 	functions:'noOrYes'
                },
                reader: {
                    totalProperty: 'total',
                    rootProperty: 'records'
                }
            }
	   }
    }
});