const URL = "http://localhost:8080/project1/user/";

let logoutEButton = document.getElementById("logoutEmployeeButton");
let logoutMButton = document.getElementById("logoutManagerButton");
let newTicketButton = document.getElementById("newTicketButton");

logoutEButton.onclick = logoutUser;
logoutMButton.onclick = logoutUser;
newTicketButton.onclick = newTicket;

//NOTE. User Data is
let userData;

$('div.employee-row').hide();
$('div.manager-row').hide();
$("#amountReim").blur(function() {
    this.value = parseFloat(this.value).toFixed(2);
});


//NOTE. Check Session
async function checkSessions() {
    let response = await fetch(URL + 'check');
    console.log(response.status);
    console.log('Hello');


    if (response.status == 404) {
        location.href = './index.html';
    }

    if (response.status == 201) {
        //catch response JSON data
        let userFromBackend = await response.json();
        userData = userFromBackend;

        if (userData.user_role_id == 1) {
            $('div.employee-row').fadeIn();
            $('#employeeUserName').text(userData.ers_username);
            $('#employeeID').text(userData.ers_user_id);
            $('#employeeFirstName').text(userData.user_first_name);
            $('#employeeLastName').text(userData.user_last_name);
            $('#employeeEmail').text(userData.user_email);

            retrieveEmployeeTicket(userData.ers_user_id);
        }
        if (userData.user_role_id == 2) {
            $('div.manager-row').fadeIn();
            $('#managerUserName').text(userData.ers_username);
            $('#managerID').text(userData.ers_user_id);
            $('#managerFirstName').text(userData.user_first_name);
            $('#managerLastName').text(userData.user_last_name);
            $('#managerEmail').text(userData.user_email);

            retrieveAllTickets(userData.ers_user_id);
        }

    }
}

//NOTE. USer features
//logout
async function logoutUser() {
    let response = await fetch(URL + "userLogout");

    if (response.status == 200) {
        location.href = './index.html';
    } else {
        console.log("woops something is wrong on backend.")
    }

    checkSessions();
}

//newTicket
async function newTicket() {
    let responseUser = await fetch(URL);
    if (responseUser.status == 404) {
        console.log("Error: can't retrieved user Data...");
    }

    if (responseUser.status == 201) {
        //catch response JSON data
        let userFromBackend = await responseUser.json();
        userData = userFromBackend;
    }


    let ticketForm = {
        reimb_type_id: document.getElementById("ticketType").value,
        reimb_amount: document.getElementById("amountReim").value,
        reimb_description: document.getElementById("descriptionReim").value,
        reimb_author: userData.ers_user_id,
    }
    console.log(`User ID in ticket form is: ${ticketForm.reimb_author}. type ID is ${ticketForm.reimb_type_id}. Amount is ${ticketForm.reimb_amount}. Description is ${ticketForm.reimb_description}`);


    let response = await fetch(URL + 'employee', {
        method: 'POST',
        body: JSON.stringify(ticketForm)
    })

    if (response.status === 201) {
        console.log("New Ticket submission is succesful");
        alert("Your ticket is submitted!");

    } else {
        console.log('New Ticket submission failed');
        alert("Your ticket failed to submit!");
    }
}

//retrieve Employee tickets
async function retrieveEmployeeTicket(userID) {

    let responseTicket = await fetch(URL + 'employee/' + userID);

    if (responseTicket.status == 200) {

        let reimbList = await responseTicket.json();
        populateEmployeeTicketList(reimbList);

    } else {
        console.log(`Can't get response. Status is ${response.status}`);

    }
}

//retrieve all Tickets
async function retrieveAllTickets(managerID) {

    let responseTicket = await fetch(URL + 'manager');
    if (responseTicket.status == 200) {
        console.log('Retrieve all tickets hit');

        let reimbList = await responseTicket.json();
        populateManagerTicketTable(reimbList, managerID);
        console.log(reimbList);

    } else {
        console.log(`Can't get response. Status is ${response.status}`);
    }
}

//retrieve all Tickets based on status
async function filterTable() {

    let statusID = document.getElementById("filterList").value;
    let managerID = userData.ers_user_id;

    let responseTicket = await fetch(URL + 'manager/sortTicket/' + statusID);

    if (responseTicket.status == 200) {
        console.log('Sort Ticket hit');

        let reimbList = await responseTicket.json();
        populateManagerTicketTable(reimbList, managerID);
        console.log(reimbList);

    } else {
        console.log(`Can't get response. Status is ${response.status}`);
    }

}
//Edit Ticket
async function editTicket(ticketID, managerID, ticketStatus) {
    console.log(`Ticket ID is ${ticketID}, ManagerID is ${managerID}, Status is ${ticketStatus}`);
    console.log(`${URL}manager/${ticketID}/${managerID}/${ticketStatus}`)
    let response = await fetch(URL + `manager/${ticketID}/${managerID}/${ticketStatus}`, {
        method: 'PUT'
    });

    if (response.status === 201) {
        console.log("Edit Ticket request is succesful gone through");
        alert("Edit Ticket request is succesful gone through");
        setTimeout("location.reload(true);", 2);
    } else {
        console.log('Edit Ticket request failed...');
        alert("Edit Ticket request failed...");
    }

}
//NOTE. Other features
function populateEmployeeTicketList(data) {
    let ticketBody = document.getElementById("ticketEmployeeBody");

    ticketBody.innerHTML = "";

    for (let ticket of data) {
        let row = document.createElement("tr");
        console.log(ticket)

        let tdReimbID = document.createElement("td");
        tdReimbID.innerText = ticket["reimb_id"];
        row.appendChild(tdReimbID);

        let tdReimbAmount = document.createElement("td");
        tdReimbAmount.innerText = "$" + ticket["reimb_amount"];
        row.appendChild(tdReimbAmount);

        let tdReimbStatus = document.createElement("td");
        tdReimbStatus.innerText = ticket["reimbStatus"];
        row.appendChild(tdReimbStatus);

        let tdReimbDesc = document.createElement("td");
        tdReimbDesc.innerText = ticket["reimb_description"];
        row.appendChild(tdReimbDesc);

        let tdReimbAuthor = document.createElement("td");
        tdReimbAuthor.innerText = ticket["reimbAuthor"];
        row.appendChild(tdReimbAuthor);

        //Ticket Resolver
        let tdReimbResolver = document.createElement("td");
        if (ticket["reimbResolver"] == null) {
            tdReimbResolver.innerText = "Waiting for Manager...";
        } else {
            tdReimbResolver.innerText = ticket["reimbResolver"];
        }
        row.appendChild(tdReimbResolver);

        let tdReimbTicketType = document.createElement("td");
        tdReimbTicketType.innerText = ticket["reimbType"];
        row.appendChild(tdReimbTicketType);

        ticketBody.appendChild(row);
    }
}

function populateManagerTicketTable(data, managerID) {
    let ticketBody = document.getElementById("ticketManagerBody");

    ticketBody.innerHTML = "";

    for (let ticket of data) {
        let row = document.createElement("tr");

        let tdReimbID = document.createElement("td");
        tdReimbID.innerText = ticket["reimb_id"];
        row.appendChild(tdReimbID);

        let tdReimbAmount = document.createElement("td");
        tdReimbAmount.innerText = "$" + ticket["reimb_amount"];
        row.appendChild(tdReimbAmount);

        let tdReimbStatus = document.createElement("td");
        tdReimbStatus.innerText = ticket["reimbStatus"];
        row.appendChild(tdReimbStatus);

        let tdReimbDesc = document.createElement("td");
        tdReimbDesc.innerText = ticket["reimb_description"];
        row.appendChild(tdReimbDesc);

        let tdReimbAuthor = document.createElement("td");
        tdReimbAuthor.innerText = ticket["reimbAuthor"];
        row.appendChild(tdReimbAuthor);

        //Ticket Resolver
        console.log(`Got reimbResolver ID: ${ticket["reimbResolver"]}`);

        let tdReimbResolver = document.createElement("td");
        if (ticket["reimbResolver"] == null) {
            tdReimbResolver.innerText = "No Manager resolved this yet";
        } else {
            tdReimbResolver.innerText = ticket["reimbResolver"];
        }
        row.appendChild(tdReimbResolver);

        let tdReimbTicketType = document.createElement("td");
        tdReimbTicketType.innerText = ticket["reimbType"];
        row.appendChild(tdReimbTicketType);

        //Approve
        let tdReimbApprove = document.createElement("td");
        let approveButton = document.createElement("button");
        approveButton.setAttribute("class", "bttn-fill bttn-sm bttn-success bttn-no-outline");
        approveButton.innerHTML = "Approve";
        // approveButton.onclick = approveTicket(ticket["reimb_id"], managerID);

        approveButton.addEventListener('click', function() {
            editTicket(ticket["reimb_id"], managerID, 1);
        });

        tdReimbApprove.appendChild(approveButton);
        row.appendChild(tdReimbApprove);

        //deny
        let tdReimbDeny = document.createElement("td");
        let denyButton = document.createElement("button");
        denyButton.setAttribute("class", "bttn-fill bttn-sm bttn-danger bttn-no-outline");
        denyButton.innerHTML = "Deny";
        // denyButton.onclick = approveTicket(ticket["reimb_id"], managerID);

        denyButton.addEventListener('click', function() {
            editTicket(ticket["reimb_id"], managerID, 2);
        });

        tdReimbDeny.appendChild(denyButton);
        row.appendChild(tdReimbDeny);

        ticketBody.appendChild(row);

        //logic of creating modal
    }
}

//NOTE. Extra