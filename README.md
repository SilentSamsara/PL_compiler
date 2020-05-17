# PL_lexer

error:

```pascal
var a,b,c,d,f,s,g:integer;
var e:string;
begin
a:=011;
b:=088;
c:=12.52;
d:=00FF;
f:=0X12;
e:="abcde\"\\FGhij 
opqr'st";
g:=321546486461;
s:=a+b+c+d;
writeln(s);
end.
```



normal:

```pascal
var a,b,c,d,f,s,g:integer;
var e:string;
begin
a:=011;
b:=077;
c:=12.52;
d:=00FF;
f:=0X12;
e:="abcde\"\\FGhij opqr'st";
g:=321;
s:=a+b+c+d;
writeln(s);
end.
```

代码中的中文编码格式为GBK

