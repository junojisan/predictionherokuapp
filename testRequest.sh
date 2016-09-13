#!/bin/sh
#curl --header "Content-type: application/json" --request POST --data '{"text": "Hello world"}' http://localhost:9000/predict
curl --header "Content-type: application/json" --request POST --data '{"text": "Hello world"}' https://predictionherokuapp.herokuapp.com/predict
