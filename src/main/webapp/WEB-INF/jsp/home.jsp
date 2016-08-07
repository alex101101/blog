<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


  <script src="resources/js/mainpage.js"></script>

  <div class="container" id="SiteBody">

    <div class="row">
      
        <div id="thumbnailInsert"></div>
      

      <template id="thumbnailInsert-template">
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">

            <div id="ThumbImg"><a href="post/{{id}}">
            <img src={{imageUrl}} class="img-responsive" alt="Responsive image">
            </a></div>

            <div class="caption">
              <h3><a id="MakeLinkBlack" href="post/{{id}}">{{title}}</a></h3>
            </div>
        </div>
        </div>
      </template>
    </div>

    
    <!-- Create a template for the main site body post object -->
    
    <div class="row">
      
      <div class="col-sm-12 col-md-9">
        <div id="mainSiteBody"></div>
      <template id="mainSiteBody-template">

        <h2 class="title"><a id="MakeLinkBlack" href="post/{{id}}">{{title}}</a></h2>

 
              <p><span id="AuthorPad" class="text-muted"> {{author}} </span><span class="text-muted" id="AuthorPad"> Date: {{dateCreated}} </span><span class="label label-info pull-md-right pull-lg-right pull-sm-left pull-xs-left"><strong>Categories: {{categories}} </strong></span>  &nbsp</p>
              


        <div id="ParaImg" class="pull-md-left pull-lg-left pull-sm-left pull-xs-left"><img src={{imageUrl}} class="img-responsive postImage" alt="Image won't load"></div>
        <div class="body"><p>{{body}}</p></div>

        <hr>



    </template>
      </div>
      <div class="col-md-3">
        <ul id="topPostInsert" class="nav nav-pills nav-stacked">
          <li role="presentation" class="active"><a href="#">Home</a></li>
          <li><h4>Top Posts</h4></li>


          <template id="topPostInsert-template">
          <li id="pill" role="presentation"><a data-toggle="tooltip" title="{{title}}" href="post/{{id}}">{{title}}</a></li>

          </template>
        </ul>
      </div>
    </div>
    <hr>
  </div><!--SiteBody-->
 	<div id="BottomSpacer"></div>
    
