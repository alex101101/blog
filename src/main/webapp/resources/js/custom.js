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
			console.log(userrest);
				addUser(userrest);
		},
		error: function() {
			alert('error loading function');
		}
	});
	$user.delegate('.editUser','click',function(){

		var $closestli = $(this).closest('li');
		console.log($closestli.find('span.firstName').html());
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
        url: "http://localhost:8080/blog/rest/accountdetails/",
        type: "GET",
        	"crossDomain":true,
        	"dataType": "json",
        		"success" : function(data) {
    	console.log("function was called");
    console.log(data);
    
  	  // then create table
  	  var results = data,
  	     i;

  	
  	var arrayLength = Object.keys(results.roles).length;
  	var arr1=[];
  	for (var i = 0; i < arrayLength; i++) {
  		arr1.push(results.roles[i].name);

  	}
  	  
  		
  	    $('#loadHere').append(
  	      '<tr>' +
  	    '<td>' + data.id + '</td>' +
  	        '<td>' + data.username + '</td>' +
  	        '<td>' + data.emailAddress + '</td>' +
  	        '<td>' + data.firstName + '</td>' +
  	        '<td>' + data.lastName + '</td>' +
  	        '<td>' + data.password + '</td>' +
  	        '<td>' + arr1 + '</td>' +
  	        '<td>' + data.dateOfRegister + '</td>' +
  	        '<td>' + data.lastLoggedIn + '</td>' +
  	        
  	      '</tr>' 
  	    );
  	  
    },
	error : function(e) {
		console.log("ERROR: ", e);

	},
	done : function(e) {
		console.log("DONE");
	}
    });
	}


