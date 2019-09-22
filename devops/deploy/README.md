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
