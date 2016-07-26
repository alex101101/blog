$(document).ready(function () {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	start();

	var $user = $('#user');
	var userTemplate = $('#user-template').html();

	function addUser(user) {
		$user.append(Mustache.render(userTemplate,user));
	}

	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/blog/rest/accountdetails/',
		success: function(userrest) {
				addUser(userrest);
		},
		error: function() {
			alert('error loading function');
		}
	});
	$user.delegate('.editUser','click',function(){

		var $closestli = $(this).closest('li');
//		console.log($closestli.find('span.firstName').html());
		$closestli.find('input.firstName').val($closestli.find('span.firstName').text());
		$closestli.find('input.lastName').val($closestli.find('span.lastName').text());
		$closestli.find('input.emailAddress').val($closestli.find('span.emailAddress').text());
		$closestli.find('input.password').val($closestli.find('span.password').text());
		$closestli.addClass('edit');
	});

	$user.delegate('.cancelEdit','click',function(){
		var $closestli = $(this).closest('li');
		$closestli.removeClass('edit');
	});
	
	$user.delegate('.deleteAccount','click',function(){
		var $closestli = $(this).closest('li');

		$.ajax({
			type: 'DELETE',
			url: 'http://localhost:8080/blog/rest/accountdetails/',
			headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    	},
			success: function(){
				
				

                alert("You will now logged out.");
                logout();
	      
				$closestli.removeClass('edit');

			},
			error: function(){
				alert('error deleting');
			}
		});
		
	});

	$user.delegate('.saveUser','click',function(){
	var $closestli = $(this).closest('li');
	var user = {
		emailAddress: $closestli.find('input.emailAddress').val(),
		firstName: $closestli.find('input.firstName').val(),
		lastName: $closestli.find('input.lastName').val(),
		password: $closestli.find('input.password').val()
	};

//	var token = $("meta[name='_csrf']").attr("content");
//	var header = $("meta[name='_csrf_header']").attr("content");


	$.ajax({
		type: 'PUT',
		url: 'http://localhost:8080/blog/rest/accountdetails/',
		headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    	},
    	data: JSON.stringify(user),
    	dataType: 'json',
		success: function(updatedUser){
			$closestli.find('span.firstName').text(user.firstName);
			$closestli.find('span.lastName').text(user.lastName);
			$closestli.find('span.emailAddress').text(user.emailAddress);
			$closestli.find('span.password').text("********");
			$closestli.removeClass('edit');
			$('#allUsersTable tbody tr').empty();
			start();

		},
		error: function(){
			alert('error saving update');
		}
	});

	});
});

function logout() {
	var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url : 'logout',
        type : 'POST',
        data: token,

        success : function(data) { 
            window.location ="/blog";    
        }, 
        error : function(data) {
            console.log(data);
        }
    });
}

function start() {
    $.ajax({
        url: "http://localhost:8080/blog/rest/accountdetails/all/",
        type: "GET",
        	"crossDomain":true,
        	"dataType": "json",
        		"success" : function(data) {
    	console.log("function was called");

    //globally save all roles to overcome lazy loading
    var save=[];
    
    $.each(data, function (i, inddata) {
    
  	  // then create table

  	//unpack roles array
  	var arrayLength = Object.keys(inddata.roles).length;
  	var arr1=[];
  	
  	for (var i = 0; i < arrayLength; i++) {
  		if(typeof (inddata.roles[i].name) !== 'undefined'){
  		arr1.push(inddata.roles[i].name);
  		save.push(inddata.roles[i].name);

  		} else {
  			arr1.push(save[inddata.roles[i]-1]);
  		}
  		

  	}
//  	console.log(arr1);
//  	console.log(arrayLength);
  	  
  		
  	    $('#loadHere').append(
  	      '<tr>' +
  	    '<td>' + inddata.id + '</td>' +
  	        '<td>' + inddata.username + '</td>' +
  	        '<td>' + inddata.emailAddress + '</td>' +
  	        '<td>' + inddata.firstName + '</td>' +
  	        '<td>' + inddata.lastName + '</td>' +
  	        '<td>' + inddata.password + '</td>' +
  	        '<td>' + arr1 + '</td>' +
  	        '<td>' + inddata.dateOfRegister + '</td>' +
  	        '<td>' + inddata.lastLoggedIn + '</td>' +
  	        
  	      '</tr>' 
  	    );
    });
  	  
    },
	error : function(e) {
		console.log("ERROR: ", e);

	},
	done : function(e) {
		console.log("DONE");
	}
    });
	}


