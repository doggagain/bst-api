package hello;
public class BinarySearchTree<T extends HaveKey> {
	private BinaryNode<T> Root;
	private int Size;
	private BinaryNode<T> Median;
	
	public BinaryNode<T> GetMedian(){
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
	
	BinaryNode<T> Insert(T info)
	{
		// Searching for a Node with given value
		BinaryNode<T> ptr = this.Root;
		BinaryNode<T> par = null; // Parent of key to be inserted

		while (ptr != null)
		{
			// If key already exists, return
			if (info.GetKey() == ptr.GetKey())
			{
				return this.Root;
			}
	
			par = ptr; // Update parent pointer
	
			// Moving on left subtree.
			if (info.GetKey() < ptr.GetKey())
			{
				if (ptr.GetIsLeftThread() == false)
					ptr = ptr.GetLeft();
				else
					break;
			}
	
			// Moving on right subtree.
			else
			{
				if (ptr.GetIsRightThread() == false)
					ptr = ptr.GetRight();
				else
					break;
			}
		}
	
		// Create a new Node
		BinaryNode<T> tmp = new BinaryNode<T>(info,true,true);

		if (par ==null)
		{
			this.Root = tmp;
			tmp.SetLeft(null);
			tmp.SetRight(null);
			this.Median=this.Root;
			this.Size=1;
			return this.Root;
		}
		else if (info.GetKey() < par.GetKey())
		{
			tmp.SetLeft(par.GetLeft());
			tmp.SetRight(par);
			par.SetIsLeftThread(false);
			par.SetLeft(tmp);
		}
		else
		{
			tmp.SetLeft(par);
			tmp.SetRight(par.GetRight());
			par.SetIsRightThread(false);
			par.SetRight(tmp);
		}
		this.UpdateMedianInsert(tmp);
		this.IncrementSize();
		return this.Root;
	}

	
	

	// Deletes a key from threaded BST with given root and
// returns new root of BST.


	public BinaryNode<T> DeleteAmen(T info)
	{
		//BinaryNode<T> root=this.Root;
		BinaryNode<T>[] nodes= this.Search(info);
		if(nodes==null){
			return null;
		}
		BinaryNode<T> par=nodes[0],ptr=nodes[1];
		this.UpdateMedianDelete(ptr);
		this.DecrementSize();
		// Initialize parent as NULL and ptrent
		// Node as root.
		
		
		// Two Children
		if (!ptr.GetIsLeftThread() && !ptr.GetIsRightThread())
			this.Root= caseC(this.Root,par, ptr);
	
		// Only Left Child
		else if (!ptr.GetIsLeftThread())
			this.Root= caseB(this.Root,par,  ptr);
	
		// Only Right Child
		else if (!ptr.GetIsRightThread())
			this.Root = caseB(this.Root,par,  ptr);
	
		// No child
		else
			this.Root= caseA(this.Root,par, ptr);
	
		return this.Root;
	}

	// Here 'par' is pointer to parent Node and 'ptr' is
	// pointer to current Node.
	public BinaryNode<T> caseA(BinaryNode<T> root,BinaryNode<T> par,BinaryNode<T> ptr)
	{
		// If Node to be deleted is root
		if (par==null)
			root = null;
	
		// If Node to be deleted is left
		// of its parent
		else if (ptr==par.GetLeft())
		{
			par.SetIsLeftThread(true);
			par.SetLeft(ptr.GetLeft());
		}
		else
		{
			par.SetIsRightThread(true);
			par.SetRight(ptr.GetRight());
		}
	
		return root;
	}

	// Here 'par' is pointer to parent Node and 'ptr' is
	// pointer to current Node.
	public BinaryNode<T> caseB(BinaryNode<T> root,BinaryNode<T> par,BinaryNode<T> ptr)
	{
		BinaryNode<T> child;
	
		// Initialize child Node to be deleted has
		// left child.
		if (!ptr.GetIsLeftThread())
			child = ptr.GetLeft();
	
		// Node to be deleted has right child.
		else
			child = ptr.GetRight();
	
		// Node to be deleted is root Node.
		if (par==null)
			root = child;
	
		// Node is left child of its parent.
		else if (ptr==par.GetLeft())
			par.SetLeft(child);
		else
			par.SetRight(child);
	
		// Find successor and predecessor
		BinaryNode<T> s = ptr.GetSuccessor();
		BinaryNode<T> p = ptr.GetPredecessor();
	
		// If ptr has left subtree.
		if (!ptr.GetIsLeftThread())
		{
			p.SetRight(s);
		}
		// If ptr has right subtree.
		else
		{
			if (!ptr.GetIsRightThread())
				s.SetLeft(p);
		}
		
		return root;
	}



	public BinaryNode<T> caseC(BinaryNode<T> root,BinaryNode<T> par,BinaryNode<T> ptr)
	{
		// Find inorder successor and its parent.
		BinaryNode<T> parsucc = ptr;
		BinaryNode<T> succ = ptr.GetRight();
	
		// // Find leftmost child of successor
		while (succ.IsParentOfLeft()) //TODO check
		{
			parsucc = succ;
			succ = succ.GetLeft();
		}

		ptr.SetInfo(succ.GetInfo());
	
		if (succ.GetIsLeftThread() && succ.GetIsRightThread())
			root = caseA(root,parsucc, succ);
		else
			root = caseB(root,parsucc, succ);
	
		return root;
	}

	public void UpdateMedianInsert(BinaryNode<T> node){
		if(this.Median==null || node==null){
			return;
		}
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		
		if(isEven && isSmaller){
			this.Median=this.Median.GetPredecessor();
		}
		
		if(!(isEven || isSmaller)){
			this.Median=this.Median.GetSuccessor();
		}
	}
	
	public void UpdateMedianDelete(BinaryNode<T> node){
		if(this.Median==null || node==null){
			return;
		}
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		
		if(!isEven && isSmaller){
			this.Median=this.Median.GetSuccessor();
		}
		
		if(isEven && !isSmaller){
			this.Median=this.Median.GetPredecessor();
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
		
		BinaryNode<T> par=null;
		BinaryNode<T> ptr=this.Root;
				// Set true if key is found
		boolean found = false;

		// Search key in BST : find Node and its
		// parent.
		while (ptr != null)
		{
			if (info.GetKey()==ptr.GetKey())
			{
				found = true;
				break;
			}
			par = ptr;
			if (info.GetKey() < ptr.GetKey())
			{
				if (!ptr.GetIsLeftThread())
					ptr = ptr.GetLeft();
				else
					break;
			}
			else
			{
				if (!ptr.GetIsRightThread())
					ptr = ptr.GetRight();
				else
					break;
			}
		}

		if (found){
			BinaryNode<T>[] arr=(BinaryNode<T>[])new BinaryNode[2];
			arr[0]=par;
			arr[1]=ptr;
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
