##!/bin/bash -e

user_name=$1
user_email=$2

git config user.name "$user_name"
git config user.email "$user_email"
