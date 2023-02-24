var getTmFc = function(){
		var today = new Date();
		var year = today.getFullYear();
		var month = today.getMonth()+1;
		var date = today.getDate();
		var tmFc = ""+year;
		if(month<10){
			tmFc += "0";
		}
		tmFc += month;
		if(date<10){
			tmFc += "0";
		}
		tmFc += date;
		tmFc += "0600";
		return tmFc;
}