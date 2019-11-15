#!/bin/sh
ng build --configuration=production
scp -r dist/ root@47.254.91.56:/root/frontend
