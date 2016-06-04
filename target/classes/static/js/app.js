$('#add_hakem').on('click', function() { 
    $('#add_hakem').append('<div class="exercise"><input type="text" name="exercise[]"><button class="remove">x</button></div>');
    return false; //prevent form submission
});

$('#exercises').on('click', '.remove', function() {
    $(this).parent().remove();
    return false; //prevent form submission
});





function checkboxlimit(checkgroup, limit){
	var checkgroup=checkgroup
	var limit=limit
	for (var i=0; i < checkgroup.length; i=i+1 )
	{
		checkgroup[i].onclick=function(){
		var checkedcount=0
		for (var i=0; i <checkgroup.length; i = i+1 )
			checkedcount+=(checkgroup[i].checked)? 1 : 0
		if (checkedcount>limit){
			alert("En fazla " +limit+ " adet anahtar kelime seçebilirsiniz!")
			this.checked=false
			}
		}
	}
};

