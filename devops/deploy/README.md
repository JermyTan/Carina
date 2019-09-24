### Deployment

#### Deploy Spring Boot
- Create a systemd file
```
sudo nano sudo nano /etc/systemd/system/carina.service
```
- Copy and past the content in app.service
- Run the following commands
```
systemctl start carina.service
systemctl enable carina.service
systemctl restart carina.service
systemctl daemon-reload
```

### Configure domain name
- Buy a domain on namecheap
- Go to Elastic IP on AWS to link instance to IP address
- Go to Route 53 link the domain
- Go to namecheap to add AWS nameswervers

### Setup Let's Encrypt
- Install certbot
```
sudo add-apt-repository ppa:certbot/certbot
sudo apt-get update
sudo apt-get install python-certbot-nginx
```
- Open nginx default 
```
sudo vim /etc/nginx/sites-available/default
```
- Change the following configuration
```
# try_files $uri $uri/ =404;
proxy_pass http://localhost:8080;
```
- Check nginx configuration is correct and reload
```
sudo nginx -t
sudo systemctl reload nginx
```
- Configure firewall
```
sudo ufw status
sudo ufw allow 'Nginx Full'
sudo certbot --nginx -d nwjbrandon.com -d www.nwjbrandon.com
```
- Select redirect options when prompted
- To renew certbot
```
sudo certbot renew --dry-run
```
- Ensure ssh remains enabled
```
sudo ufw allow ssh
```

### Resources
- https://www.baeldung.com/spring-boot-app-as-a-service
- https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html
- https://medium.com/@manjiki/running-spring-boot-applications-as-system-services-on-linux-5ea5f148c39a
- http://techgenix.com/namecheap-aws-ec2-linux/
- https://www.digitalocean.com/community/tutorials/how-to-set-up-let-s-encrypt-with-nginx-server-blocks-on-ubuntu-16-04
- https://www.digitalocean.com/community/tutorials/how-to-setup-a-firewall-with-ufw-on-an-ubuntu-and-debian-cloud-server
