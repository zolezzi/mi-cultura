#/bin/sh
rm -Rf client/web/

curl http://localhost:8080/v2/api-docs -H "Accept: application/json" -H "Content-Type: application/json" -X GET > swagger.json

java -jar swagger-codegen-cli-2.3.1.jar generate  -i swagger.json  -o client -l typescript-angular --api-package service --model-package model

cp -R client/service ../web-app/src/app/api/
cp -R client/model ../web-app/src/app/api/
cp -R client/*.ts ../web-app/src/app/api/

rm -R client/