const URL = "http://localhost:8080/project1/";

// let loginForm = document.getElementById("loginForm");
let loginButton = document.getElementById("loginButton");
// let signupForm = document.getElementById("signupForm");
let signupButton = document.getElementById("signupButton");

// loginForm.onclick = getLoginForm;
loginButton.onclick = loginUser;
// signupForm.onclick = getSignupForm;
signupButton.onclick = signupUser;

function getSignupUser() {
    let newers_username = document.getElementById("usernameSignup").value;
    let newers_password = document.getElementById("passwordSignup").value;
    let newers_first_name = document.getElementById("firstNameSignup").value;
    let newers_last_name = document.getElementById("lastNameSignup").value;
    let newers_email = document.getElementById("emailSignup").value;
    let newers_role_id = document.getElementById("userTypeSignup").value;

    let user = {
        ers_username: newers_username,
        ers_password: newers_password,
        user_first_name: newers_first_name,
        user_last_name: newers_last_name,
        user_email: newers_email,
        user_role_id: newers_role_id
    }

    return user;
}

function getLoginUser() {

    let newers_email = document.getElementById("emailLogin").value;
    let newers_password = document.getElementById("passwordLogin").value;

    let user = {
        user_email: newers_email,
        ers_password: newers_password
    }

    return user;
}
async function signupUser() {

    let user = getSignupUser();

    let response = await fetch(URL + 'userSignup', {
        method: 'POST',
        body: JSON.stringify(user)
            // credentials: 'include'
    });


    if (response.status === 201) {
        console.log("User Registration is successful");
        alert("Registration is succesful!");

    } else {
        console.log('User Registration failed');
        alert("Registration failed!");
    }
}

async function loginUser() {
    let user = getLoginUser();

    let response = await fetch(URL + "userLogin", {
        method: 'POST',
        body: JSON.stringify(user)
    });

    if (response.status === 201) {
        console.log("User Login is successful");
        location.href = './userPage.html';

    } else {
        console.log('User Login failed');
        alert("Login failed!");
    }
}