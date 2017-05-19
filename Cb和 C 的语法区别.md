# Cb 和 C 在语法上的区别
1. 下列 C 语言的功能不会出现在 Cb 中

* 预处理器

  制作预处理器工作量太大。所以 Cb 中无法使用#define 和#Include。但是不能使用#include 关键字，就无法导入类型定义和函数原型。为了解决这个问题，Cb 采用了和 java 类似的 import 关键字。

* K&R 语法

  [指的是 C 语言从 C99之后支持在一个 block 中变量声明不需要全部放在 block 的开头了。](http://stackoverflow.com/questions/7859424/why-was-mixing-declarations-and-code-forbidden-up-until-c99)但是在 Cb 中变量的声明仍然需要全部放在 block（还是function？） 的开头，至于为什么要这么做，暂时还不知道。

* 浮点数

  暂时先不实现。

* enum

  为了简化，暂不实现

* 结构体（struct）的 位域（bit field）

  为了简化，暂不实现。

* 结构体和联合体（union）的赋值

  C 语言中可以通过 var = {1,2,3} 这样的写法来初始化结构体或数组，但是 Cb 中不支持这样的语法。Cb 中支持的语法是？？？

* 逗号表达式

  C 语言中有一种特殊的运算符逗号，它的优先级最低。如(3+5, 6+8) 是逗号表达式，表达式的值是最右边的表达式的值，即14.

* ~~const~~(说好的不出现呢，结果作者的编译器代码里是实现了的)

* volatile

* auto

* register
2. 在结构体和联合体的定义上，C 语言对于一个类型可以用逗号分隔定义多个成员，而 Cb 中不支持这样的语法。例如C语言中可以这样写，

```C
struct point {
	int x,y;
}
```
Cb 中只能这样写
```C
struct point {
	int x;
	int y;
}
```


3. 变量定义的语法是 Cb 和 C 最显著的差异。在 Cb 中数组、指针和函数指针都采用统一的后置记法。例如 C 语言中定义 int 类型的代码如下所示

```C
int x[5]
```

Cb中采用的是 java 的风格

```java
int[5] x;
int (long, char *, ...) a;
```

下面这行代码在 C 和 Cb 中的含义不一样。

```C
int *x,y;	
```

C 语言中这样定义表示 x 是 int *类型，y 是 int 类型。而在 Cb 中这样定义表示 x 和 y 都是 int *类型。总而言之，Cb 是把类型定义和变量名完全分开，类型定义全部在左边，右边剩下的才是变量名。这样的目的一是为了解析方便，二是觉得这样的代码在可读性上也要比 C 语言好。

4. Cb中函数的参数声明有如下3种

   * 无参数（形参声明为 void。如 getc 等）
   * 定长参数
   * 不定长参数（如 printf，scanf 等）

   所以 Cb 中函数参数声明不能写成

   ```C
   int func();
   ```

   这样的形式，无参数的情况下一定要在括号里面加上 void（修改一下解析器，似乎括号里为空似乎也不影响？）

5. C 语言在定义结构体的同时可以定义该类型的变量，Cb 中两者必须分开定义

6. Cb 中在类型定义之前就可以编写用到了该类型的代码，例如如下代码是合法的

   ```C
   struct s var;
   struct s {
     int memb;
   };
   ```

   书中说在 C 语言中不允许编写这样的代码，但是在 gcc 上尝试了发现是可以的！




# 编写Cb编译器参考资料

一般编程语言的语法单位有下面这些：

- 定义（definition）
- 声明（declaration）
- 语句（statement）
- 表达式（expression）
- 项（term）

“定义”是指变量定义、函数定义或类定义等

函数或方法的定义的本体中包含有“语句”，如 if 语句，while 语句，for 语句等

“表达式”是比语句小、具有值的语法单位。具有值，是指将表达式写在赋值的右侧，或者在函数调用时写在参数的位置等。if、while、for 是语句，所以不具有值。

项是表达式中构成二元运算的一方，也就是仅由一元运算符构成的语法。

定义包含语句，语句包含表达式，表达式包含项。



### 类树

#### 一、Node 类树

```
Node 
	AST
	ExprNode
		AbstractAssignNode
			AssignNode
			OpAssignNode
		AddressNode
		BinaryOpNode
			LogicalAndNode
			LogicalOrNode
		CastNode
		CondExprNode
		FuncallNode
		LHSNode						能够成为赋值的左值的节点
			ArefNode				数组表达式( a[i] )
			DereferenceNode
			MemberNode				成员表达式 (s.memb)
			PtrMemberNode			成员表达式 (ptr->memb)
			VariableNode
		LiteralNode
			IntegerLiteralNode
			StringLiteralNode
		SizeofExprNode
		SizeofTypeNode
		UnaryOpNode					一元运算表达式( +x, -x, ...)
			UnaryArithmeticOpNode	 ++ 和 --
				PrefixOpNode		前置的 ++ 和 --
				SuffixOpNode		后置的 ++ 和 --
	Slot
	StmtNode
		BlockNode
		BreakNode
		CaseNode
		ContinueNode
		DoWhileNode
		ExprStmtNode
		ForNode
		GotoNode
		IfNode
		LabelNode
		ReturnNode
		SwitchNode
		WhileNode
	TypeDefinition
		CompositeTypeDefinition
			StructNode
			UnionNode
		TypedefNode
	TypeNode
```



