<div class="container" id="SiteBody">
	<script src="resources/js/custom.js"></script>
		
	 <div class="container" id="SiteBody">
	User access area!!

	
	<div class="table-responsive">
	<table class="table">
		<thead>
		  <tr>
		  	<th>ID</th>
		    <th>User Name</th>
		    <th>Email Address</th>
		    <th>First Name</th> 
		    <th>Last Name</th>
		    <th>Password</th>
		   	<th>Roles</th>
		    <th>Date of Register</th>
		    <th>Last Logged in</th>
		  </tr>
		 </thead>
		  <tbody id="loadHere">
  			</tbody>
		</table>
	</div>

    <template id="user-template">
      
      <li data-id="{{id}}">
      <p>
      First Name: <span class="noedit firstName">{{firstName}}</span> <input class="edit firstName" />
      </p>
      <p>
      Last Name: <span class="noedit lastName">{{lastName}}</span> <input class="edit lastName"/>
      </p>
      <p>
      Email: <span class="noedit emailAddress">{{emailAddress}}</span> <input class="edit emailAddress"/>
      </p>
      <p>
      Password: <span class="noedit password">********</span> <input type="password" class="edit password"/>
      </p>
      <p>
      <button data-id="{{id}}" class="deleteAccount">Delete Account</button>
    </p>
    <p>
       <button class="editUser noedit">Edit Details</button>
     </p>
     <p>
       <button class="saveUser edit">Save Details</button>
     </p>
     <p>
       <button class="cancelEdit edit">Cancel</button>
     </p>
       </li>

    </template>
    <ul id="user" class="list-unstyled">

    </ul>
  <hr>
  </div><!--SiteBody-->

    
	</div>
