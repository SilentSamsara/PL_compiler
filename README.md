# PL_compiler




normal:

```pascal
program test;
begin
x:=19;
y:=2;
z:=y;
for i:=100 downto 15 do
if x<y+15 then y:=x
else z:=z*7+x
end.

```


error:

```pascal
program test;
begin
x:=19;
for i:=100 downto 15 do
if x<y+15 then y:=x
else z:=z*7+x
end.
```