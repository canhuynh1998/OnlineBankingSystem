
document.addEventListener('DOMContentLoaded', ()=>{
getName("api/v1/customer");


async function getName(url) {
fetch(url)
    .then(response => response.json())
    .then(data => {
        document.getElementById("name").innerHTML = data.name;
    });
}

const savingCreate = document.getElementById('saving-create');
savingCreate.addEventListener('click', async () => {
try {
    const response = await fetch('api/v1/account/create/1', {
      method: 'post',
    });
    console.log('Completed!', response);
    savingCreate.setAttribute("disabled",true);
  } catch(err) {
    console.error(`Error: ${err}`);
  }
});


const checkingCreate = document.getElementById('checking-create');
checkingCreate.addEventListener('click', async () => {
  try {
    const response = await fetch('api/v1/account/create/0', {
      method: 'post',
    });
    console.log('Completed!', response);
    checkingCreate.setAttribute("disabled",true);
  } catch(err) {
    console.error(`Error: ${err}`);
  }
});

getAccountInfo('api/v1/account') ;

async function getAccountInfo(url) {
fetch(url)
    .then(response => response.json())
    .then(data => {
        data.forEach(element => {
            if(element.type === 0){
                document.getElementById("checking-number").innerHTML = data.number;
                document.getElementById("checking-balance").innerHTML = data.balance;
            }else{
                document.getElementById("saving-number").innerHTML = data.number;
                document.getElementById("saving-balance").innerHTML = data.balance;
            }
        })
    });
}
});