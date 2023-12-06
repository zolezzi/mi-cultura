import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
  stages: [
    { duration: '5s', target: 2 }, // Simula 2 usuarios durante 5 segundos
    { duration: '10s', target: 5 }, // Mantén 5 usuarios durante 10 segundos
    { duration: '5s', target: 0 },  // Deja de enviar tráfico gradualmente hasta llegar a 0 usuarios
  ],
};

export default function () {

  let userLoginVO = {
    email: 'adminmicultura@gmail.com',
    password: 'HolaPlaneta11!',
  };

  let loginRes = http.post('http://localhost:8080/login', JSON.stringify(userLoginVO), {
    headers: {
	  'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
  });

  let authToken = loginRes.json('token');
  let userId = loginRes.json('userId'); 
  
  let headers = {
    headers: {
	  'Accept': 'application/json',
	  'Content-Type': 'application/json',
      Authorization: `Bearer ${authToken}`,
    },
  };

  let userRes = http.get(`http://localhost:8080/find-by-id/${userId}`, headers);
  check(userRes, {
    'User request status is 200': (r) => r.status === 200,
  });

  sleep(1);
}