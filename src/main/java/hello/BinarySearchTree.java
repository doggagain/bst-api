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
	
	public void Insert(BinaryNode<T> node){
		BinaryNode<T> x= this.Root;
		
		if(x==null){
			this.Root=node;
			this.Median=this.Root;
			this.Size=1;
			return;
		}
		
		while(!x.IsLeaf()){
			if(node.GetKey() < x.GetKey()){
				if(x.IsParentOfLeft()){
					x=x.GetLeft();
				}else{
					break;
				}
			}else{
				if(x.IsParentOfRight()){
					x=x.GetRight();
				}else{
					break;
				}
			}
		}
		
		node.SetParent(x);
		if(node.GetKey() < x.GetKey()){
			node.SetLeft(x.GetLeft());
			x.SetLeft(node);
			node.SetRight(x);
		}else{
			node.SetRight(x.GetRight());
			x.SetRight(node);
			node.SetLeft(x);
		}
		this.UpdateMedianInsert(node);
		this.IncrementSize();
	}
	
	
	
	public BinaryNode<T> Delete(T info){
		BinaryNode<T> searchResult= this.Search(info);
		if(searchResult==null){
			return null;
		}
		this.UpdateMedianDelete(searchResult);
		this.DecrementSize();
		
		BinaryNode<T> predecessor=searchResult.GetPredecessor();
		BinaryNode<T> successor=searchResult.GetSuccessor();
		
		BinaryNode<T> twoChildrenResult=ReassignIfHasTwoChildren(searchResult,predecessor,successor);
		if(twoChildrenResult!=null){
			return twoChildrenResult;
		}

		ReassignPredecessorSuccessor(searchResult,predecessor,successor);
		
		BinaryNode<T> leafResult=ReassignIfLeaf(searchResult,predecessor,successor);
		if(leafResult!=null){
			return leafResult;
		}
		
		return DeleteOneChild(searchResult);
	}

	public BinaryNode<T> DeleteWork(T info){
		System.out.println("dude, seriously?");
		BinaryNode<T> z= this.Search(info);
		if(z==null){
			return null;
		}
		BinaryNode<T> y=null;
		BinaryNode<T> x=null;
		this.UpdateMedianDelete(z);
		this.DecrementSize();
		
		if(!z.IsLeaf()){
			y=z;
		}else{
			y=z.GetSuccessor();
		}

		if(y.GetLeft()!=null && y.GetLeft().GetParent()==y){
			x=y.GetLeft();
		}else if(y.GetRight()!=null && y.GetRight().GetParent()==y){
			x=y.GetRight();
		}else{
			x=null;
		}

		if(x!=null){
			if(y==null){
				x.SetParent(null);
			}else{
				x.SetParent(y.GetParent());
			}
		}

		if(y.GetParent()==null){
			this.Root=x;
		}else{
			if(y.IsLeftChild()){
				y.GetParent().SetLeft(x);
			}else{
				y.GetParent().SetRight(x);
			}
		}

		if(y!=z){
			z.SetInfo(y.GetInfo());
		}
		return y;
	}
	
	// Deletes a key from threaded BST with given root and
// returns new root of BST.


public BinaryNode<T> DeleteAmen(T info)
{
	BinaryNode<T> ptr= this.Search(info);

	if(ptr==null){
		return null;
	}

	if(ptr.IsMinimumLeaf()){

	}
	// Initialize parent as NULL and ptrent
    // Node as root.
    
    
    // Two Children
	if ((ptr.IsParentOfLeft() || ptr.GetLeft()==null) &&
		(ptr.IsParentOfLeft() || ptr.GetLeft()==null))
        this.Root = caseC(this.Root, ptr);
 
    // Only Left Child
    else if (ptr.IsParentOfLeft() || ptr.GetLeft()==null)
		this.Root= caseB(this.Root,  ptr);
 
    // Only Right Child
    else if ((ptr.IsParentOfLeft() || ptr.GetLeft()==null))
		this.Root = caseB(this.Root,  ptr);
 
    // No child
    else
		this.Root= caseA(this.Root, ptr);
 
    return this.Root;
}

// Here 'par' is pointer to parent Node and 'ptr' is
// pointer to current Node.
BinaryNode<T> caseA(BinaryNode<T> root,BinaryNode<T> ptr)
{
    // If Node to be deleted is root
    if (ptr.GetParent()==null)
        root = null;
 
    // If Node to be deleted is left
    // of its parent
    else if (ptr.IsLeftChild())
    {
		ptr.GetParent().SetLeft(ptr.GetLeft());
    }
    else
    {
        ptr.GetParent().SetRight(ptr.GetRight());
    }
 
    return root;
}

// Here 'par' is pointer to parent Node and 'ptr' is
// pointer to current Node.
BinaryNode<T> caseB(BinaryNode<T> root,BinaryNode<T> ptr)
{
    BinaryNode<T> child;
 
    // Initialize child Node to be deleted has
    // left child.
    if (ptr.IsParentOfLeft() || ptr.GetLeft()==null)
        child = ptr.GetLeft();
 
    // Node to be deleted has right child.
    else
        child = ptr.GetRight();
 
    // Node to be deleted is root Node.
    if (ptr.GetParent()==null)
        root = child;
 
    // Node is left child of its parent.
    else if (ptr.IsLeftChild())
        ptr.GetParent().SetLeft(ptr.GetLeft());
    else
		ptr.GetParent().SetRight(ptr.GetRight());
 
    // Find successor and predecessor
    BinaryNode<T> s = ptr.GetSuccessor();
    BinaryNode<T> p = ptr.GetPredecessor();
 
    // If ptr has left subtree.
	if ((ptr.IsParentOfLeft() || ptr.GetLeft()==null) && p!=null)
	{
        p.SetRight(s);
	}
    // If ptr has right subtree.
    else
    {
		if ((ptr.IsParentOfRight() || ptr.GetRight()==null) && s!=null)
            s.SetLeft(p);
	}
	
    return root;
}

BinaryNode<T> caseC(BinaryNode<T> root,BinaryNode<T> ptr)
{
    // Find inorder successor and its parent.
    BinaryNode<T> succ = ptr.GetSuccessor().GetMinium();
 
    ptr.SetInfo(succ.GetInfo());
 
	if ((!succ.IsParentOfLeft() && succ.GetLeft()!=null) && 
		(!succ.IsParentOfRight() && succ.GetRight()!=null))
        root = caseA(root, succ);
    else
        root = caseB(root, succ);
 
    return root;
}


	public void ReassignPredecessorSuccessor(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		
		if( predecessor!=null && 
			predecessor.GetRight()==searchResult && 
			predecessor!=searchResult.GetParent() && 
			searchResult!=predecessor.GetParent()){
			predecessor.SetRight(successor);
		}
		
		if( successor!=null && 
			successor.GetLeft()==searchResult &&
			successor!=searchResult.GetParent()&&
			searchResult!=successor.GetParent()){
			successor.SetLeft(predecessor);
		}
	}
	
	public BinaryNode<T> ReassignIfLeaf(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		if(searchResult.IsLeaf()){
			
			if(searchResult.IsLeftChild()){
				
				searchResult.GetParent().SetLeft(predecessor);
				return searchResult;
			}else if(searchResult.IsRightChild()){
				
				searchResult.GetParent().SetRight(successor);
				return searchResult;
			}else{
				this.Root=null;
				return searchResult;
			}
			
		}
		return null;
	}
	
	public BinaryNode<T> ReassignIfHasTwoChildren(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		if(searchResult.IsParentOfLeft() && searchResult.IsParentOfRight()){
			
			BinaryNode<T> deleteResult=this.Delete(successor.GetInfo());
			
			T info = searchResult.GetInfo();
			searchResult.SetInfo(deleteResult.GetInfo());
			deleteResult.SetInfo(info);
			return deleteResult;
		}
		return null;
	}
	
	public BinaryNode<T> DeleteOneChild(BinaryNode<T> node){
		
		if(node.GetParent()==null){ //if for in case of root
			if(node.IsParentOfRight()){
				this.Root=node.GetRight();
			}else{
				this.Root=node.GetLeft();
			}
		}

		BinaryNode<T> child=null; //get your child
		if(node.IsParentOfLeft()){
			child=node.GetLeft();
		}else{
			child=node.GetRight();
		}

		if(node.IsLeftChild()){
			node.GetParent().SetLeft(child);
		}
		if(node.IsRightChild()){
			node.GetParent().SetRight(child);
		}
		child.SetParent(node.GetParent());
		return node;
	}
	
	public void UpdateMedianInsert(BinaryNode<T> node){
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
	
	public BinaryNode<T> Search(T info){
		BinaryNode<T> current=this.Root;
		while(!current.IsLeaf() && current.GetKey()!=info.GetKey()){
			if(info.GetKey()< current.GetKey()){
				
				if(current.IsParentOfLeft()){
					current=current.GetLeft();
				}else{
					return null;
				}
				
			}else{
				
				if(current.IsParentOfRight()){
					current=current.GetRight();
				}else{
					return null;
				}
			}
		}
		if(current.GetKey()==info.GetKey()){
			return current;
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
