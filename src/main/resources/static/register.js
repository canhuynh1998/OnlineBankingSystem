console.log("Connected");
const signUp = document.getElementById("sign-up");
signUp.addEventListener('click', () =>{


    const name = document.getElementById('name').value;
    const sex = document.getElementById('male').checked? 'M': 'F';
    const dob = document.getElementById('dob').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const pin = document.getElementById('pin').value;
    const url =  'api/v1/registration';
    console.log(url);
    const newUser = {
    "name": name,
    "sex": sex,
    "dob": dob,
    "userName": username,
    "password": password,
    "pin": pin
}
fetch(url, {
    method:'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(newUser)
})
.then(res=>res.json())
.then(data=> console.log(data))

}