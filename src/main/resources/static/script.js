
document.addEventListener('DOMContentLoaded', ()=>{
getName("api/v1/customer");
getAccountInfo('api/v1/account') ;

async function createAccount(typeOfAccount){
    const url = 'api/v1/account/create/'+typeOfAccount;
    console.log(url);

        try {
            const response = await fetch(url, {
              method: 'POST',
              headers: {
                    'Content-Type': 'application/json '
                  }
            });
            console.log('Completed!', response);
            getAccountInfo('api/v1/account');
          } catch(err) {
            console.error(`Error: ${err}`);
          }

}

const savingCreate = document.getElementById('saving-create');
savingCreate.addEventListener('click', async () => { createAccount('1') });
//try {
//    const response = await fetch('api/v1/account/create/1', {
//      method: 'post',
//    });
//    console.log('Completed!', response);
//    window.location.reload();
//  } catch(err) {
//    console.error(`Error: ${err}`);
//  }
//});

const checkingCreate = document.getElementById('checking-create');
checkingCreate.addEventListener('click', async () => {
  try {
    const response = await fetch('api/v1/account/create/0', {
      method: 'post',
    });
    console.log('Completed!', response);
    window.location.reload();
  } catch(err) {
    console.error(`Error: ${err}`);
  }
});

const savingDeposit = document.getElementById('saving-deposit');
savingDeposit.addEventListener('click', () =>{ updateAccount('amount-saving','deposit','1')} );

const checkingDeposit = document.getElementById('checking-deposit');
checkingDeposit.addEventListener('click', () =>{ updateAccount('checking-saving','deposit','0')} );

async function updateAccount(buttonId, operation, typeOfAccount){
const amount = document.getElementById(buttonId).value;

    const url = 'api/v1/account/'+operation+'/'+typeOfAccount;
    console.log(url);
    if(amount != ''){
        try {
            const response = await fetch(url, {
              method: 'PUT',
              headers: {
                    'Content-Type': 'application/json '
                  },
            body: amount
            });
            console.log('Completed!', response);
            getAccountInfo('api/v1/account');
          } catch(err) {
            console.error(`Error: ${err}`);
          }
    }
}

async function getName(url) {
fetch(url)
    .then(response => response.json())
    .then(data => {
        document.getElementById("name").innerHTML = data.name;
    });
}

async function getAccountInfo(url) {
fetch(url)
    .then(response => response.json())
    .then(data => {
        data.forEach(element => {
            if(element.type === 0){
                document.getElementById("checking-number").innerHTML = element.number;
                document.getElementById("checking-balance").innerHTML = element.balance;
            }else{
                document.getElementById("saving-number").innerHTML = element.number;
                document.getElementById("saving-balance").innerHTML = element.balance;
            }
        })
    });
}
});