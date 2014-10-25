fn main() {
	let a = 1i;
	let b;
	let c : Test = Test {x : 100};
	let (d:Int, e:Test) = (100, Test {x:200});
	if a == 15i {
        let Test {x:f};
        let Test {x:ref g};
        <caret>
	}
}