#!/bin/bash

echo "INFO: Creating S3 buckets if they do not exist..."

buckets=("soundify-audio-bucket-upd")
region="eu-north-1"

existing_buckets=$(awslocal s3api list-buckets --query 'Buckets[].Name' --output text)

for bucket in "${buckets[@]}"; do
    if ! echo "$existing_buckets" | grep -q "$bucket"; then
        awslocal s3 mb s3://"$bucket" --region "$region"
        echo "INFO: S3 bucket '$bucket' created."
    else
        echo "INFO: S3 bucket '$bucket' already exists. Skipping creation."
    fi
done
