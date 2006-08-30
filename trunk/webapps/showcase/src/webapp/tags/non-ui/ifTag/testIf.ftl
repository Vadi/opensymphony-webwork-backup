<html>
	<head>
		<title>TEST IF</title>
	</head>
<body>
<p>
This is a simple freemarker template to test the If Tag (using freemarker directive).
There's 18 combination being tested. The characters in bold and non-bold should be the same.
</p>	
	
	
<b>1 - Foo -</b>
<@ww.if test="true">
	Foo
</@ww.if>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>2 - Bar -</b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>3 - FooFooFoo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="true">
		FooFoo	
	</@ww.if>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.if>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>4 - FooBarBar - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.if>
<hr/>
<b>5 - BarFooFoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.else>
	Bar
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.else>
<hr/>
<b>6 - BarBarBar - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.else>
<hr/>
<b>7 - Foo - </b>
<@ww.if test="true">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>8 - Moo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>9 - Bar - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>10 - FooFooFoo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>11 - FooMooMoo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>12 - FooBarBar - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>13 - MooFooFoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>14 - MooMooMoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>15 - MooBarBar - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>
<hr/>
<b>16 - BarFooFoo - </b>
<@ww.if test="false">	
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.else>	
<hr/>
<b>17 - BarMooMoo - </b>
<@ww.if test="false">	
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.else>	
<hr/>	
<b>18 - BarBarBar - </b>
<@ww.if test="false">	
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
	<@ww.else>
		BarBar
	</@ww.else>
</@ww.else>

<hr/>
<b>19 - Foo - </b> 
<@ww.if test="true">
	Foo
</@ww.if>

<hr/>
<b>20 - ** should not display anything ** - </b>
<@ww.if test="false">
	Foo
</@ww.if>

<hr/>
<b>21 FooFooFoo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
</@ww.if>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>22 - Foo -  </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
</@ww.if>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>23 - BarFooFoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.else>
	Bar
	<@ww.if test="true">
		FooFoo
	</@ww.if>
</@ww.else>

<hr/>
<b>24 - Bar - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
</@ww.else>

<hr/>
<b>25 - FooFooFoo</b>
<@ww.if test="true">
	Foo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>26 - FooMooMoo</b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<b>27 - Foo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<b>28 - MooFooFoo</b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>


<b>29 - MooMooMoo</b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>


<b>30 - Moo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>


<b>31 - BarFooFoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="true">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.else>

<b>32 - BarMooMoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="true">
		MooMoo
	</@ww.elseif>
</@ww.else>

<b>33 - Bar - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
	<@ww.elseif test="false">
		MooMoo
	</@ww.elseif>
</@ww.else>

<hr/>
<b>34 - FooFooFoo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>35 - Foo - </b>
<@ww.if test="true">
	Foo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>36 - MooFooFoo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="true">
		FooFoo
	</@ww.if>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>37 - Moo - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="true">
	Moo
	<@ww.if test="false">
		FooFoo
	</@ww.if>
</@ww.elseif>
<@ww.else>
	Bar
</@ww.else>

<hr/>
<b>38 - BarFooFoo  - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="true">
		FooFoo
	</@ww.if>
</@ww.else>

<hr/>
<b>39 - Bar  - </b>
<@ww.if test="false">
	Foo
</@ww.if>
<@ww.elseif test="false">
	Moo
</@ww.elseif>
<@ww.else>
	Bar
	<@ww.if test="false">
		FooFoo
	</@ww.if>
</@ww.else>

</body>
</html>

