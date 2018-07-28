package hello;

import javax.sound.midi.MidiDevice.Info;

public class BinarySearchTree<T extends HaveKey> {
	private BinaryNode<T> Root;
	private int Size;
	private T Median;
	
	public T GetMedian(){
		return this.Median;
	}
	
	public BinaryNode<T> GetRoot(){
		return this.Root;
	}
	
	public void SetRoot(BinaryNode<T> root){
		this.Root=root;
	}
	
	public int GetSize(){
		return this.Size;
	}
	
	public void IncrementSize(){
		this.Size++;
	}
	
	public void DecrementSize(){
		this.Size--;
	}
	
	//Get the correct parent for inserted node
	//input: the info of the to be inserted node
	//output: the parent of the node to be inserted
	public BinaryNode<T> GetCorrectParent(T info) {
		BinaryNode<T> currentNode = this.Root;
		BinaryNode<T> parent = null; 

		while (currentNode != null)
		{
			// If key already exists, return
			if (info.GetKey() == currentNode.GetKey())
			{
				return null;
			}
	
			parent = currentNode; // Update parentent pointer
			if (info.GetKey() < currentNode.GetKey()) //go to left tree
			{
				if (currentNode.GetIsLeftThread() == false)
					currentNode = currentNode.GetLeft();
				else
					break;
			}
			else//go to right tree
			{
				if (currentNode.GetIsRightThread() == false)
					currentNode = currentNode.GetRight();
				else
					break;
			}
		}
		return parent;
	}

	//Insert new node to tree
	//input: the info of the to be inserted node
	//output: the root of the tree
	public BinaryNode<T> Insert(T info)
	{
		BinaryNode<T> parent=this.GetCorrectParent(info);
	
		// init the node to be leaf with nulls as children
		BinaryNode<T> nodeToInsert = new BinaryNode<T>(info,true,true);

		if (parent ==null)//if the to be inserted node is the root
		{
			this.Root = nodeToInsert;
			nodeToInsert.SetLeft(null);
			nodeToInsert.SetRight(null);
			this.Median=this.Root.GetInfo(); //set median and size to initialize them
			this.Size=1;
			return this.Root;
		}
		else if (info.GetKey() < parent.GetKey()) //if the to be inserted node is a left child
		{
			nodeToInsert.SetLeft(parent.GetLeft());
			nodeToInsert.SetRight(parent); //thread to parent
			parent.SetIsLeftThread(false); //parent has a real child on the left
			parent.SetLeft(nodeToInsert);
		}
		else//if the to be inserted node is a right child
		{
			nodeToInsert.SetLeft(parent); //thread to parent
			nodeToInsert.SetRight(parent.GetRight());
			parent.SetIsRightThread(false); //parent has a real child on the tight
			parent.SetRight(nodeToInsert);
		}
		this.UpdateMedianInsert(nodeToInsert);//update the median value of the tree
		this.IncrementSize();//increase the size of the tree
		return this.Root;
	}

	
	

	//Delete a node that is present in the tree
	//input: the info of the to be deleted node
	//output: the info of the deleted node
	public T Delete(T info)
	{
		BinaryNode<T>[] nodes= this.Search(info); //get the to be deleted node and its parent 
		if(nodes==null){ //if not found then cant delete
			return null; 
		}
		BinaryNode<T> parent=nodes[0],currentNode=nodes[1]; //extract nodes
		T infoOfFoundNode=currentNode.GetInfo(); //get the info of the to be deleted node
		this.UpdateMedianDelete(currentNode);//update the median value of the tree
		this.DecrementSize();//decrease the size of the tree
	
		
		if (!currentNode.GetIsLeftThread() && !currentNode.GetIsRightThread()){ //check if has two real children
			this.Root= applyHasNoChild(this.Root,parent, currentNode);
		}
		// Only Left Child
		else if ((!currentNode.GetIsLeftThread()) || //check if has left child or
				 (!currentNode.GetIsRightThread())){  // check has a right child
			this.Root= applyHasOneChild(this.Root,parent,  currentNode);
		}
		else { //finally has no children
			this.Root= applyHasTwoChildren(this.Root,parent, currentNode);
		}
		return infoOfFoundNode;
	}

	//Apply handling when the node to delete Has two children
	//input: the root, the parent of node to delete and the node to delete
	//output: the new root of the tree
	public BinaryNode<T> applyHasTwoChildren(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		if (parent==null) // check node to delete is root
			root = null;
	
		else if (currentNode==parent.GetLeft()) //check if the node is a left child
		{
			parent.SetIsLeftThread(true);    //then the child is left thread
			parent.SetLeft(currentNode.GetLeft()); //set the new child of parent to the nodes child
		}
		else
		{
			parent.SetIsRightThread(true); //then the child is right thread
			parent.SetRight(currentNode.GetRight());//set the new child of parent to the nodes child
		}
	
		return root;
	}
	//apply reassignments to the successor and predecessor of the node to delete
	//input:  the node to delete
	//output: none
	public void ReassignSuccessorPredecessor(BinaryNode<T> currentNode){
		BinaryNode<T> successor = currentNode.GetSuccessor(); // get successor and predecessor of the node to delete
		BinaryNode<T> predecessor = currentNode.GetPredecessor();
	
		
		if (!currentNode.GetIsLeftThread()) // check if the left child is real and is subtree
		{
			predecessor.SetRight(successor); //connect  predecessor to successor with right thread
		}
		else
		{
			if (!currentNode.GetIsRightThread()) // check if the left child is real and is subtree
				successor.SetLeft(predecessor); //connect  successor to predecessor with left thread
		}
		
	}

	//Apply handling when the node to delete Has one child
	//input: the root, the parent of node to delete and the node to delete
	//output: the new root of the tree
	public BinaryNode<T> applyHasOneChild(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		BinaryNode<T> child; //hold a reference to the child
	
	
		if (!currentNode.GetIsLeftThread()){ //if the left child is not a thread then the child is the left
			child = currentNode.GetLeft();
		}
		else{				//else go to right child
			child = currentNode.GetRight();
		}

		if (parent==null){ // check node to delete is root
			root = child;
		}
		else if (currentNode==parent.GetLeft()){ // check if the node is a left or a right child
			parent.SetLeft(child);
		}
		else{
			parent.SetRight(child);
		}

		ReassignSuccessorPredecessor(currentNode);

		return root;
	}



	//Apply handling when the node to delete Has no child
	//input: the root, the parent of node to delete and the node to delete
	//output: the new root of the tree
	public BinaryNode<T> applyHasNoChild(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		BinaryNode<T>[] successorAndParent=GetSuccessorAndParentOfRightSubtree(currentNode);
		BinaryNode<T> parentOfTheSuccessor = successorAndParent[0];
		BinaryNode<T> successor = successorAndParent[1];

		currentNode.SetInfo(successor.GetInfo()); //switch the info of node to delete 
												//with the info of the minimum in the right subtree
		//After switch, apply the correct deletion on the new tree 
		if (successor.GetIsLeftThread() && successor.GetIsRightThread()) {
			root = applyHasTwoChildren(root,parentOfTheSuccessor, successor); //to go has two children and update root
		}
		else{
			root = applyHasOneChild(root,parentOfTheSuccessor, successor);//to go has one child and update root
		}
		return root;
	}

	//Get the minimum value in right sutree and its parent
	//input: the node to delete
	//output: the minimum value in right sutree and its parent
	public BinaryNode<T>[] GetSuccessorAndParentOfRightSubtree(BinaryNode<T> currentNode){
		BinaryNode<T> parentOfTheSuccessor = currentNode; // hold reference to parent of the right node, 
													    //which is the root of the right subtree
		BinaryNode<T> successor = currentNode.GetRight(); //the root of the right subtree
	
		while (successor.IsParentOfLeft()) //get the minimum value in right sutree, while updating parent
		{
			parentOfTheSuccessor = successor;
			successor = successor.GetLeft();
		}

		BinaryNode<T>[] successorAndParent=(BinaryNode<T>[])new BinaryNode[2]; //return the pair
		successorAndParent[0]=parentOfTheSuccessor;
		successorAndParent[1]=successor;
		return successorAndParent;
	}

	//Update the median with its correct predecessor or succsessor, if needed
	//input: the root, the parent of node to delete and the node to delete
	//output: the new root of the tree
	public void UpdateMedianInsert(BinaryNode<T> node){
		BinaryNode<T>[] nodes=this.Search(this.Median);
		BinaryNode<T> medianNode=nodes[1];
		if(this.Median==null || node==null){
			return;
		}
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		
		if(isEven && isSmaller){
			BinaryNode<T> predecessor=medianNode.GetPredecessor();
			this.Median=predecessor==null? null: predecessor.GetInfo();
		}
		
		if(!(isEven || isSmaller)){
			BinaryNode<T> succsessor=medianNode.GetSuccessor();
			this.Median=succsessor ==null ? null: succsessor.GetInfo();
		}
	}
	
	public void UpdateMedianDelete(BinaryNode<T> node){
		if(this.Median==null || node==null){
			return;
		}
		BinaryNode<T>[] nodes=this.Search(this.Median);
		BinaryNode<T> medianNode=nodes[1];
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		boolean isBigger = node.GetKey() > this.Median.GetKey();
		boolean isSame = !isSmaller && !isBigger;
		
		if((!isEven && isSmaller)||(!isEven && isSame)){
			BinaryNode<T> succsessor=medianNode.GetSuccessor();
			this.Median=succsessor ==null ? null: succsessor.GetInfo();
		}
		
		if((isEven && isBigger)||(isEven && isSame)){
			BinaryNode<T> predecessor=medianNode.GetPredecessor();
			this.Median=predecessor==null? null: predecessor.GetInfo();
		}
	}
	
	public BinaryNode<T> GetMinimum(){
		return this.Root.GetMinium();
	}
	
	public BinaryNode<T> GetMaximum(){
		return this.Root.GetMaximum();
	}
	
	public  BinaryNode<T> GetPredecessor(BinaryNode<T> node){
		return node.GetPredecessor();
	}
	
	public  BinaryNode<T> GetSuccessor(BinaryNode<T> node){
		return node.GetSuccessor();
	}
	
	public BinaryNode<T>[] Search(T info){
		
		BinaryNode<T> parent=null;
		BinaryNode<T> currentNode=this.Root;
				// Set true if key is found
		boolean found = false;

		// Search key in BST : find Node and its
		// parentent.
		while (currentNode != null)
		{
			if (info.GetKey()==currentNode.GetKey())
			{
				found = true;
				break;
			}
			parent = currentNode;
			if (info.GetKey() < currentNode.GetKey())
			{
				if (!currentNode.GetIsLeftThread())
					currentNode = currentNode.GetLeft();
				else
					break;
			}
			else
			{
				if (!currentNode.GetIsRightThread())
					currentNode = currentNode.GetRight();
				else
					break;
			}
		}

		if (found){
			BinaryNode<T>[] arr=(BinaryNode<T>[])new BinaryNode[2];
			arr[0]=parent;
			arr[1]=currentNode;
			return arr;
		}
		return null;
	}

	public String[] InorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Inorder(this.Root,keys,index);
		return keys;
	}

	public void Inorder(BinaryNode<T> node,String[] keys,int[] index){
		if(node.IsParentOfLeft()){
			Inorder(node.GetLeft(),keys,index);
		}
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
		if(node.IsParentOfRight()){
			Inorder(node.GetRight(),keys,index);
		}
	}

	
	public String[] PreorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Preorder(this.Root,keys,index);
		return keys;
	}

	public void Preorder(BinaryNode<T> node,String[] keys,int[] index){
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
		if(node.IsParentOfLeft()){
			Preorder(node.GetLeft(),keys,index);
		}
		if(node.IsParentOfRight()){
			Preorder(node.GetRight(),keys,index);
		}
	}

	
	public String[] PostorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Postorder(this.Root,keys,index);
		return keys;
	}	

	public void Postorder(BinaryNode<T> node,String[] keys,int[] index){
		if(node.IsParentOfLeft()){
			Postorder(node.GetLeft(),keys,index);
		}
		if(node.IsParentOfRight()){
			Postorder(node.GetRight(),keys,index);
		}
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
	}
}
