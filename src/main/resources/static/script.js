getName("api/v1/customer");
//getAccountInfo('api/v1/account') ;

const savingCreate = document.getElementById('saving-create');
savingCreate.addEventListener('click', () => {
    createAccount('1');
    savingCreate.disabled = true;
});

const checkingCreate = document.getElementById('checking-create');
checkingCreate.addEventListener('click', () => {
    createAccount('0');
    checkingCreate.disabled = true;
});


const savingDeposit = document.getElementById('saving-deposit');
savingDeposit.addEventListener('click', () =>{ updateAccount('amount-saving','deposit','1')} );

const checkingDeposit = document.getElementById('checking-deposit');
checkingDeposit.addEventListener('click', () =>{ updateAccount('amount-checking','deposit','0')} );

const savingWithdraw = document.getElementById('saving-withdraw');
savingWithdraw.addEventListener('click', () =>{ updateAccount('amount-saving','withdraw','1')} );

const checkingWithdraw = document.getElementById('checking-withdraw');
checkingWithdraw.addEventListener('click', () =>{ updateAccount('amount-checking','withdraw','0')} );


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
