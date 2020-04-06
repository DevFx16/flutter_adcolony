git add -A
user=$(git config user.name)
now=$(date +%m-%d-%Y)
echo "[$user][$now]"
read -p "Enter commit message: " message
git commit -m "[$user][$now] $message"
git push origin master