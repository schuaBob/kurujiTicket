請務必使用我們的servers來設定專案


/Servers/Tomcat v9.0 Server at localhost-config/web.xml
要將default 的url pattern 的 / 刪除，line:432
最底下welcome file的設定全部刪除




/Servers/Tomcat v9.0 Server at localhost-config/server.xml
line:152行，將path改成/

etc: path="/"


!important!

eclipse 跟console同欄的servers
進去設定
admin port 8015
http 8090
AJP 8019

Server Options務必將Serve modules without publishing 打勾!!!!!!一定要打勾