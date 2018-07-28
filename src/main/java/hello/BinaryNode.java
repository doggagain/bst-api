package hello;
public class BinaryNode<T extends HaveKey> extends HaveKey {
	public BinaryNode<T> Left;
	public BinaryNode<T> Right;
	public T Info;
	boolean IsLeftThread; //fasle if left child is a real child and not a thread
	boolean IsRightThread;//fasle if right child is a real child and not a thread
	
	public BinaryNode(T info,BinaryNode<T> left,BinaryNode<T> right){
		this.Info=info;
		this.Left=left;
		this.Right=right;
	}

	public BinaryNode(T info){
		this.Info=info;
	}

	public BinaryNode(T info,boolean isLeftThread,boolean isRightThread){
		this.Info=info;
		this.IsLeftThread=isLeftThread;
		this.IsRightThread=isRightThread;
	}
	

	//GetInfo of node
	//input:  none
	//output: Info of node
	public T GetInfo(){
		return this.Info;
	}
	
	
	//SetInfo of node
	//input:  new info
	//output: none
	public void SetInfo(T info){
		this.Info=info;
	}

	//GetLeft of node
	//input:  none
	//output: Left of node
	public BinaryNode<T> GetLeft(){
		return this.Left;
	}
	
	public void SetLeft(BinaryNode<T> left){
		this.Left=left;
	}
	
	
	//GetRight of node
	//input:  none
	//output: Right of node
	public BinaryNode<T> GetRight(){
		return this.Right;
	}
	
	public void SetRight(BinaryNode<T> right){
		this.Right=right;
	}

	
	//GetIsLeftThread of node
	//input:  none
	//output: IsLeftThread of node
	public boolean GetIsLeftThread(){
		return this.IsLeftThread;
	}
	
	public void SetIsLeftThread(boolean isLeftThread){
		this.IsLeftThread=isLeftThread;
	}
	//GetIsRightThread of node
	//input:  none
	//output: IsRightThread of node
	public boolean GetIsRightThread(){
		return this.IsRightThread;
	}
	
	public void SetIsRightThread(boolean isRightThread){
		this.IsRightThread=isRightThread;
	}

	//Get key of info of node
	//input:  none
	//output: key of info of node
	public int GetKey() {
		return this.GetInfo().GetKey();
	}

	//Check if right child is not null and real child of this node
	//input:  none
	//output: if right child is not null and real child of this node
	public boolean IsParentOfRight(){
		return this.GetRight()!=null && !this.GetIsRightThread();
	}
	
	//Check if left child is not null and real child of this node
	//input:  none
	//output: if left child is not null and real child of this node
	public boolean IsParentOfLeft(){
		return this.GetLeft()!=null && !this.GetIsLeftThread();
	}

	//Get the GetPredecessor of the node
	//input:  none
	//output: Predecessor of the node
	public  BinaryNode<T> GetPredecessor(){
		if(!this.IsParentOfLeft()){ //check if left is a thread, and therefore the predecessor
			return this.Left;
		}
		if(this.IsParentOfLeft()){ //check if left is a real child, and therefore the predecessor
									//is the maximum value in the left subtree
			return this.Left.GetMaximum();
		}
		return null; //then we are the minimum node;
	}

	//Get the GetSuccessor of the node
	//input:  none
	//output: Successor of the node
	public  BinaryNode<T> GetSuccessor(){
		if(!this.IsParentOfRight()){//check if left is a thread, and therefore the successor
			return this.Right;
		}
		if(this.IsParentOfRight()){//check if right is a real child, and therefore the successor
			//is the minimum value in the right subtree
			return this.Right.GetMinium();
		}
		return null; //then we are the maximum node;
	}
	//GetMinium of the subtree rooted in node
	//input:  none
	//output: Minium of the subtree rooted in node
	public BinaryNode<T> GetMinium(){
		BinaryNode<T> currentRoot=this;
		while(currentRoot.IsParentOfLeft()){ //if has a real left child
			currentRoot=currentRoot.GetLeft(); //then go to left tree
		}
		return currentRoot;
	}

	//GetMaximum of the subtree rooted in node
	//input:  none
	//output: Maximum of the subtree rooted in node
	public BinaryNode<T> GetMaximum(){
		BinaryNode<T> currentRoot=this;
		while(currentRoot.IsParentOfRight()){//if has a real right child
			currentRoot=currentRoot.GetRight();//then go to right tree
		}
		return currentRoot;
	}
	
}
