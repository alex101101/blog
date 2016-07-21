$(document).ready(function () {
	//initialise tooltips for toppost pills
	$('#pill').tooltip();
	//initialise multi select plugin
	$('#inputCategories').multiselect({
		includeSelectAllOption: true,
        maxHeight: 400,
        dropUp: true
	});

	//Main Site forms
	var $mainBody = $('#mainSiteBody');
	var $thumbnailInsert = $('#thumbnailInsert');
	var $topPostInsert = $('#topPostInsert');
	var mainSiteBodyTemplate = $('#mainSiteBody-template').html();
	var mainSiteThumbnailTemplate = $('#thumbnailInsert-template').html();
	var mainSiteTopPostTemplate = $('#topPostInsert-template').html();

	var $inputAuthor=('#inputAuthor');
	var $inputTitle=('#inputTitle');
	var $inputBody=('#inputBody');
	var $inputTopPost=('#inputTopPost');
	var $inputThumbnail=('#inputThumbnail');
	var $inputCategories=('#inputCategories');
	var $inputImageUrl=('#inputImageUrl');
	var $inputVideoUrl=('#inputVideoUrl');


	function addBody(body) {
	$mainBody.append(Mustache.render(mainSiteBodyTemplate,body));
	}

	function addThumbnail(thumbnail) {
	$thumbnailInsert.append(Mustache.render(mainSiteThumbnailTemplate,thumbnail));
	}

	function addTopPost(topPost) {
	$topPostInsert.append(Mustache.render(mainSiteTopPostTemplate,topPost));
	}

	$.ajax({
	type: 'GET',
	url: 'http://localhost:8080/blog/rest/blog/',
	success: function(blogrest) {
		console.log('CALLED');

		var count=0;
		var countTop=0;
		


		$.each(blogrest, function (i, indPost) {
					//unpack categories array
		  	var arrayLength = Object.keys(indPost.categories).length;
		  	var arr1=[];
		  	for (var i = 0; i < arrayLength; i++) {

  			arr1.push(indPost.categories[i].categoryName);
  				}
  				console.log(arr1);
  				indPost.categories=arr1;

			addBody(indPost);
			var modifiedPost = {};

			//clone json object to modify for shortened title in thumbnail headlines
			var modifiedPost = JSON.parse(JSON.stringify(indPost));

			//Populate Thumbnails x 4
			if (modifiedPost.thumbnail==true) {

				count++;
				console.log('PRINT');
				if (count < 5) {
					if ((modifiedPost.title).length>13) {
					
  					var titleShort = modifiedPost.title.substring(0,14);
  					titleShort+="...";

  					modifiedPost.title=titleShort;
  				}
					addThumbnail(modifiedPost);
				}
				else {
					console.log('Too many thumbnails to load')
				}
			}

			//Populat Top Posts x 3
			if (indPost.topPost==true) {
				countTop++;
				if (countTop < 4) {
					addTopPost(indPost);
				}
				else {
					console.log('Too many top posts to load');
				}
			}

			});
	},
	error: function() {
		alert('error loading function');
	}
	});

	$('#createPost').on('click', function() {
		var post = {
			title: $inputTitle.val(),
			byline: '',
			categories: $inputCategories.val(),
			author: $inputAuthor.val(),
			body: $inputBody.val(),
			imageUrl: $inputImageUrl.val(),
			videoUrl: $inputVideoUrl.val(),
			topPost: $inputTopPost.val(),
			thumbnail: $inputThumbnail.val()
		};
		$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/blog/rest/blog/',
			data: post,
			success: function() {
				console.log('Post is successful');
			},
			error: function() {
				console.log('unable to post');
			}

	});
	});


	//User Forms
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

	$user.delegate('.saveUser','click',function(){
	var $closestli = $(this).closest('li');
	var user = {
		emailAddress: $closestli.find('input.emailAddress').val(),
		firstName: $closestli.find('input.firstName').val(),
		lastName: $closestli.find('input.lastName').val(),
		password: $closestli.find('input.password').val()
	};

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");


	$.ajax({
		type: 'PUT',
		url: 'http://localhost:8080/blog/rest/accountdetails/',
		headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    	},
    	data: JSON.stringify(user),
    	dataType: 'json',
		// beforesend: function(xhr) {
		// 	xhr.setRequestHeader(header, token);
		// },
		success: function(updatedUser){
			$closestli.find('span.firstName').text(user.firstName);
			$closestli.find('span.lastName').text(user.lastName);
			$closestli.find('span.emailAddress').text(user.emailAddress);
			$closestli.find('span.password').text(user.password);
			$closestli.removeClass('edit');

		},
		error: function(){
			alert('error saving update');
		}
	});

	});
});