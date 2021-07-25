const URL = "http://localhost:8080/project1/user/";

let logoutButton = document.getElementById("logoutButton");

logoutButton.onclick = logoutUser;

//NOTE. User Data is
let userData;

$('div.employee-row').hide();
$('div.manager-row').hide();

async function checkSessions() {
    let response = await fetch(URL);
    console.log(response.status);
    console.log('Hello');


    if (response.status == 404) {
        location.href = './index.html';
    }

    if (response.status == 201) {
        //catch response JSON data
        let userFromBackend = await response.json();
        userData = userFromBackend;
        console.log(userData.user_role_id);

        if (userData.user_role_id == 1) {
            $('div.employee-row').fadeIn();
        }
        if (userData.user_role_id == 2) {
            $('div.manager-row').fadeIn();
        }

    }
}


//functions
async function logoutUser() {
    let response = await fetch(URL + "userLogout");

    if (response.status == 200) {
        location.href = './index.html';
    } else {
        console.log("woops something is wrong on backend.")
    }
}