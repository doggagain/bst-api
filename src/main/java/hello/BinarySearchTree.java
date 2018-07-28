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
			this.Median=this.Root.GetInfo();
			this.Size=1;
			return this.Root;
		}
		else if (info.GetKey() < parent.GetKey()) //if the to be inserted node is a left child
		{
			nodeToInsert.SetLeft(parent.GetLeft());
			nodeToInsert.SetRight(parent);
			parent.SetIsLeftThread(false);
			parent.SetLeft(nodeToInsert);
		}
		else//if the to be inserted node is a right child
		{
			nodeToInsert.SetLeft(parent);
			nodeToInsert.SetRight(parent.GetRight());
			parent.SetIsRightThread(false);
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
		this.UpdateMedianDelete(currentNode);
		this.DecrementSize();
		// Initialize parentent as NULL and currentNodeent
		// Node as root.
		
		
		// Two Children
		if (!currentNode.GetIsLeftThread() && !currentNode.GetIsRightThread())
			this.Root= caseC(this.Root,parent, currentNode);
	
		// Only Left Child
		else if (!currentNode.GetIsLeftThread())
			this.Root= caseB(this.Root,parent,  currentNode);
	
		// Only Right Child
		else if (!currentNode.GetIsRightThread())
			this.Root = caseB(this.Root,parent,  currentNode);
	
		// No child
		else
			this.Root= caseA(this.Root,parent, currentNode);
	
		return infoOfFoundNode;
	}

	// Here 'parent' is pointer to parentent Node and 'currentNode' is
	// pointer to current Node.
	public BinaryNode<T> caseA(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		// If Node to be deleted is root
		if (parent==null)
			root = null;
	
		// If Node to be deleted is left
		// of its parentent
		else if (currentNode==parent.GetLeft())
		{
			parent.SetIsLeftThread(true);
			parent.SetLeft(currentNode.GetLeft());
		}
		else
		{
			parent.SetIsRightThread(true);
			parent.SetRight(currentNode.GetRight());
		}
	
		return root;
	}

	// Here 'parent' is pointer to parentent Node and 'currentNode' is
	// pointer to current Node.
	public BinaryNode<T> caseB(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		BinaryNode<T> child;
	
		// Initialize child Node to be deleted has
		// left child.
		if (!currentNode.GetIsLeftThread())
			child = currentNode.GetLeft();
	
		// Node to be deleted has right child.
		else
			child = currentNode.GetRight();
	
		// Node to be deleted is root Node.
		if (parent==null)
			root = child;
	
		// Node is left child of its parentent.
		else if (currentNode==parent.GetLeft())
			parent.SetLeft(child);
		else
			parent.SetRight(child);
	
		// Find successor and predecessor
		BinaryNode<T> s = currentNode.GetSuccessor();
		BinaryNode<T> p = currentNode.GetPredecessor();
	
		// If currentNode has left subtree.
		if (!currentNode.GetIsLeftThread())
		{
			p.SetRight(s);
		}
		// If currentNode has right subtree.
		else
		{
			if (!currentNode.GetIsRightThread())
				s.SetLeft(p);
		}
		
		return root;
	}



	public BinaryNode<T> caseC(BinaryNode<T> root,BinaryNode<T> parent,BinaryNode<T> currentNode)
	{
		// Find inorder successor and its parentent.
		BinaryNode<T> parentsucc = currentNode;
		BinaryNode<T> succ = currentNode.GetRight();
	
		// // Find leftmost child of successor
		while (succ.IsParentOfLeft()) //TODO check
		{
			parentsucc = succ;
			succ = succ.GetLeft();
		}

		currentNode.SetInfo(succ.GetInfo());
	
		if (succ.GetIsLeftThread() && succ.GetIsRightThread())
			root = caseA(root,parentsucc, succ);
		else
			root = caseB(root,parentsucc, succ);
	
		return root;
	}

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
