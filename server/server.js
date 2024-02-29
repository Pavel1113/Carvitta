const mysql = require('mysql2');
const http = require('http');
var fs = require('fs');
const querystring = require('querystring');
const { log } = require('console');
const { URLSearchParams } = require('url');
const colors = require('colors');
const { threadId } = require('worker_threads');


const indexHTML = fs.readFileSync('./login.html');
const adminka = fs.readFileSync('./adminka.html');

const connection = mysql.createConnection({
  host: 'MYSQL6008.site4now.net',
  user: 'a98c86_windson',
  password: 'b7W5uUbA',
  database: 'db_a98c86_windson'
});

connection.connect((err) => {
  if (err) {
    console.error('Ошибка подключения к базе данных:', err);
    return;
  }
  connection.query("SET SESSION wait_timeout = 3600", function(err,result){
    if(err) throw err;
  })
  connection.query("SELECT @@wait_timeout", function(err,result){
    if(err) throw err;
  })
  console.log(`<------------------- DATABASE IS TRY TO CONNECT ------------------->`.bgMagenta);
  setTimeout(() => {
    console.log(`
██████╗  █████╗ ████████╗ █████╗ ██████╗  █████╗ ███████╗███████╗     ██████╗ ██████╗ ███╗   ██╗███╗   ██╗███████╗ ██████╗████████╗███████╗██████╗ 
██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║████╗  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔══██╗
██║  ██║███████║   ██║   ███████║██████╔╝███████║███████╗█████╗      ██║     ██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██║        ██║   █████╗  ██║  ██║
██║  ██║██╔══██║   ██║   ██╔══██║██╔══██╗██╔══██║╚════██║██╔══╝      ██║     ██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██║        ██║   ██╔══╝  ██║  ██║
██████╔╝██║  ██║   ██║   ██║  ██║██████╔╝██║  ██║███████║███████╗    ╚██████╗╚██████╔╝██║ ╚████║██║ ╚████║███████╗╚██████╗   ██║   ███████╗██████╔╝
╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝ ╚═════╝   ╚═╝   ╚══════╝╚═════╝ 
  
  `.green);
  }, 1900);
});

var isAuth=0;

function checkAuth(login, pass){
  if(login == 'admin' && pass=='admin'){
    isAuth = 1;
    return true;
  }else{
    isAuth = 0;
    return false;
  }
}

const server = http.createServer((request,response)=>{
  switch(request.url){
      case "/login":
          if(request.method === "GET"){
            if(isAuth == 1){
              response.writeHead(302,{'Location':'/adminka'});
              response.end();
            }else{
              response.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
              response.end(indexHTML);
            }
            break
          }else if(request.method === "POST" && request.url === "/login"){
            let body ='';
            request.on('data',data =>{
              body+=data
            });

            request.on('end',()=>{
              const form = querystring.parse(body)
              if(checkAuth(form.username,form.password)){
                console.log(isAuth.toString(),'proverka success')
                console.log(form.username,form.password);
                response.writeHead(302,{'Location':'/adminka'});
                response.end();
              }else{
                response.writeHead(404,{'Content-Type':'text/html; charset=utf-8'});
                response.end()
              }
            })
          }
      case "/adminka":
        if(request.method === "GET"){
          if(isAuth == 1){
            response.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
            response.end(adminka);
          }else{
            response.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
            response.end("<h1 style='color: red'>В доступе отказано</h1>");
          }  
        }
      case "/addUser":
        if(request.method === "POST" && request.url === "/addUser"){
          let body1 ='';
            request.on('data',data1 =>{
              body1+=data1.toString();
            });
            request.on('end',()=>{
              const params = JSON.parse(body1);
              var login = params.p_login;
              var name = params.p_name;
              var password = params.p_password;
              var isAuth = params.p_isAuth
              const paramssss = [login,name,password,isAuth]
              connection.query('CALL addUser(?,?,?,?)',paramssss, (error,results) =>{
                if (error) {
                  console.error('Error calling stored procedure: ', error);
                  return;
                }
                console.log('coonection', results);
                
              })
              response.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
              response.end();       
            })
        }
        break
        case "/addCar":
          if(request.method === "POST" && request.url === "/addCar"){
            let body1 ='';
              request.on('data',data1 =>{
                body1+=data1.toString();
              });
              request.on('end',()=>{
                const params = JSON.parse(body1);
                var usid = params.p_usid;
                var marka = params.p_marka;
                var model = params.p_model;
                var price = params.p_price;
                var year = params.p_year;
                var mehauto = params.p_mehauto;
                var volume = params.p_volume;
                var typefuel = params.p_typefuel;
                var shape = params.p_shape;
                var distance = params.p_distance;
                var img1 = params.p_img;
                var img2 = params.p_img2;
                var phone = params.p_phone;
                var issell = params.p_issell;
                console.log(marka);
                const paramssss = [usid,marka,model,price,year,mehauto,volume,typefuel,shape,distance,img1,img2,phone,issell]
                connection.query('CALL addCar(?,?,?,?,?,?,?,?,?,?,?,?,?,?)',paramssss, (error,results) =>{
                  if (error) {
                    console.error('Error calling stored procedure: ', error);
                    return;
                  }
                  console.log('coonection', results);
                  
                })
                response.writeHead(200,{'Content-Type':'text/html; charset=utf-8'});
                response.end();       
              })
          }
          break
      case "/auth":
        if(request.method === "POST" && request.url === "/auth"){
          let body1 ='';
            request.on('data',data1 =>{
              body1+=data1.toString();
            });
            request.on('end',async ()=>{
              const params = JSON.parse(body1);
              var login = params.p_login;
              var password = params.p_password; 
              const paramss = [login,password]
              console.log(paramss)
              connection.query('CALL auth(?,?)',paramss,(error,result,fields)=>{
                if(error){
                  console.log(error)
                  return;
                }
                response.writeHead(200,{'Content-Type':'application/json'});
                response.end(JSON.stringify(result[0]));
              })
                  
            })
        }
        break
      case "/checkLogin":
        if(request.method === "POST" && request.url === "/checkLogin"){
          let body1 ='';
            request.on('data',data1 =>{
              body1+=data1.toString();
            });
            request.on('end',async ()=>{
              const params = JSON.parse(body1);
              var login = params.p_login;
              const paramss = [login]
              console.log(paramss)
              connection.query('CALL checkLogin(?)',paramss,(error,result,fields)=>{
                if(error){
                  console.log(error)
                  return;
                }
                console.log(result[0])
                response.writeHead(200,{'Content-Type':'application/json'});
                response.end(JSON.stringify(result[0]));
              })
                  
            })
        }
        break
      case "/enter":
        if(request.method === "POST" && request.url === "/enter"){
          let body1 ='';
            request.on('data',data1 =>{
              body1+=data1.toString();
            });
            request.on('end',async ()=>{
              const params = JSON.parse(body1);
              var login = params.p_login;
              const paramss = [login]
              console.log(paramss)
              connection.query('CALL enter(?)',paramss,(error,result,fields)=>{
                if(error){
                  console.log(error)
                  return;
                }
                
                response.writeHead(200,{'Content-Type':'application/json'});
                response.end();
              })
                  
            })
        }
        break
      case "/exit0":
          if(request.method === "POST" && request.url === "/exit0"){
            let body1 ='';
              request.on('data',data1 =>{
                body1+=data1.toString();
              });
              request.on('end',async ()=>{
                const params = JSON.parse(body1);
                var login = params.p_login;
                const paramss = [login]
                console.log(paramss)
                connection.query('CALL exit0(?)',paramss,(error,result,fields)=>{
                  if(error){
                    console.log(error)
                    return;
                  }
                  response.writeHead(200,{'Content-Type':'application/json'});
                  response.end();
                })
                    
              })
          }else{
            isAuth = 0;
            console.log("success exit done!");
            response.end(indexHTML);
          }
          break
      case "/checkAuthUser":
        if(request.method === "POST" && request.url === "/checkAuthUser"){
          let body1 ='';
            request.on('data',data1 =>{
              body1+=data1.toString();
            });
            request.on('end',async ()=>{
              const params = JSON.parse(body1);
              var isAuth = params.p_isAuth;
              const paramss = [isAuth]
              console.log(paramss)
              connection.query('CALL checkAuthUser(?)',paramss,(error,result,fields)=>{
                if(error){
                  console.log(error)
                  return;
                }
                console.log(result[0])
                response.writeHead(200,{'Content-Type':'application/json'});
                response.end(JSON.stringify(result[0]));
              })
                  
            })
        }
        break;
        case "/getAllUsers":
          if(request.method === "POST" && request.url === "/getAllUsers"){
            let body1 ='';
              request.on('data',data1 =>{
                body1+=data1.toString();
              });
              request.on('end',async ()=>{
                
                connection.query('CALL getAllUsers()',(error,result,fields)=>{
                  if(error){
                    console.log(error)
                    return;
                  }
                  console.log(result[0])
                  response.writeHead(200,{'Content-Type':'application/json'});
                  response.end(JSON.stringify(result[0]));
                })
                    
              })
          }
          break;
        case "/getAllCars":
          if(request.method === "POST" && request.url === "/getAllCars"){
            let body1 ='';
              request.on('data',data1 =>{
                body1+=data1.toString();
              });
              request.on('end',async ()=>{
                const params = JSON.parse(body1);
                var issell = params.p_issell;
                const paramss = [issell]
                console.log(paramss)
                connection.query('CALL getAllCars(?)',paramss,(error,result,fields)=>{
                  if(error){
                    console.log(error)
                    return;
                  }
                  console.log(result[0])
                  response.writeHead(200,{'Content-Type':'application/json'});
                  response.end(JSON.stringify(result[0]));
                })
                    
              })
          }
          break;
          case "/getMyCars":
            if(request.method === "POST" && request.url === "/getMyCars"){
              let body1 ='';
                request.on('data',data1 =>{
                  body1+=data1.toString();
                });
                request.on('end',async ()=>{
                  const params = JSON.parse(body1);
                  var usid = params.p_usid;
                  const paramss = [usid]
                  console.log(paramss)
                  connection.query('CALL getMyCars(?)',paramss,(error,result,fields)=>{
                    if(error){
                      console.log(error)
                      return;
                    }
                    console.log(result[0])
                    response.writeHead(200,{'Content-Type':'application/json'});
                    response.end(JSON.stringify(result[0]));
                  })
                      
                })
            }
            break;
            case "/sellCar":
              if(request.method === "POST" && request.url === "/sellCar"){
                let body1 ='';
                  request.on('data',data1 =>{
                    body1+=data1.toString();
                  });
                  request.on('end',async ()=>{
                    const params = JSON.parse(body1);
                    var id = params.p_id;
                    const paramss = [id]
                    console.log(paramss)
                    connection.query('CALL sellCar(?)',paramss,(error,result,fields)=>{
                      if(error){
                        console.log(error)
                        return;
                      }
                      
                      response.writeHead(200,{'Content-Type':'application/json'});
                      response.end();
                    })
                        
                  })
              }
              break
  }
})

server.listen(5559,'localhost');
console.log(`

██████╗ █████╗ ██████╗ ██╗   ██╗██╗████████╗████████╗ █████╗     ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗ 
██╔════╝██╔══██╗██╔══██╗██║   ██║██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗
██║     ███████║██████╔╝██║   ██║██║   ██║      ██║   ███████║    ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝
██║     ██╔══██║██╔══██╗╚██╗ ██╔╝██║   ██║      ██║   ██╔══██║    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗
╚██████╗██║  ██║██║  ██║ ╚████╔╝ ██║   ██║      ██║   ██║  ██║    ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║
 ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝    ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝
 
 `.red);