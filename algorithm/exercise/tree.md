---
title: 大数运算
date: 2020-04-06
categories: ["算法"]
tag: ["算法"]
---
[toc]

# 树





##### 面试题 04.06. 后继者

> 设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。

```c
TreeNode* inorderSuccessor(TreeNode* root, TreeNode* p) {
    TreeNode * ans =nullptr;     
    while(root){
        cout << root->val << endl;
        if(root->val <= p->val) root = root->right;
        else  ans = root,root = root->left; 
    }
    return ans;
}
```



##### 173. 二叉搜索树迭代器

> 实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
>
> 调用 `next()` 将返回二叉搜索树中的下一个最小的数。

```c
BSTIterator(TreeNode* root) {
        ldfs(root);
}
void ldfs(TreeNode* root) {
    while (root) {
        st.emplace(root);
        root = root->left;
    }
}
int next() {
    TreeNode* root = st.top(); st.pop();
    ldfs(root->right);
    return root->val;
}
```

这道题就是前序和中序遍历二叉树的非递归版本的差分版。



#### [101. 对称二叉树](https://leetcode-cn.com/problems/symmetric-tree/)

> 给定一个二叉树，检查它是否是镜像对称的。

递归和非递归, 非递归都是使用一个stack 来模拟出这个栈

```c
bool isSymmetric(TreeNode* root) {
    if(root==nullptr) return true;
    return helper(root->left,root->right);
}
bool helper(TreeNode* left, TreeNode* right){
    if(left == nullptr && right == nullptr) return true;
    if(left == nullptr || right == nullptr) return false;
    if(left->val != right->val) return false;
    return helper(left->left, right->right) && helper(left->right, right->left);
}
```

```c
bool isSymmetric(TreeNode* root) {
    if(root == NULL) return true;
    stack<TreeNode*> le, ri;
    le.push(root->left);
    ri.push(root->right);
    TreeNode * t1, * t2;
    while(true){
        if(le.empty() && ri.empty()) return true;
        if(le.empty() || ri.empty()) return false;
        t1 = le.top(); t2 = ri.top();
        le.pop(); ri.pop();
        if(t1 == NULL && t2 == NULL){
            continue;
        } 
        if(t1== NULL || t2 == NULL) return false;
        if(t1->val != t2->val) return false;
        le.push(t1->right);ri.push(t2->left);
        le.push(t1->left); ri.push(t2->right);
    }
    return true;
}
```



#### [437. 路径总和 III](https://leetcode-cn.com/problems/path-sum-iii/)

>给定一个二叉树，它的每个结点都存放着一个整数值。
>
>找出路径和等于给定数值的路径总数。
>
>路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

```c
int pathSum(TreeNode* root, int sum) {
    if(root ==nullptr) return 0;
    int ans = 0;
    ans += pathSum(root->left,sum);
    ans += pathSum(root->right,sum);
    sumHelper(0,root,ans,sum);
    return ans;
}
void sumHelper(int cur, TreeNode* root,int &ans, int sum){
    if(root == nullptr) return;
    cur += root->val;
    if(cur == sum) ans++;
    sumHelper(cur,root->left,ans,sum);
    sumHelper(cur,root->right,ans,sum);
}
```

这是一道双递归的题目， 在父类一个递归， 子类一个递归！！



#### [面试题 04.04. 检查平衡性](https://leetcode-cn.com/problems/check-balance-lcci/)

> 实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。

```c
bool isBalanced(TreeNode* root) {
    if(root == nullptr) return true;
    int maxLeft = 0, maxRight = 0;
    search(root->left,0,maxLeft), search(root->right,0,maxRight);
    return abs(maxRight - maxLeft) <= 1 && 
        isBalanced(root->left) && isBalanced(root->right); 
}

void search(TreeNode* root,int cur, int &maxi){
    if(root == nullptr) return;
    cur += 1;
    if(root->left == nullptr && root->right ==nullptr)    maxi = max(maxi,cur);   
    search(root->left,cur,maxi), search(root->right,cur,maxi);
}
```

简单题





#### [94. 二叉树的中序遍历](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/)

> 给定一个二叉树，返回它的*中序* 遍历。

```c
vint inorderTraversal(TreeNode* root) {
    stack<TreeNode*> s;
    vint ans;
    TreeNode* index = root;
    while(index || !s.empty()){
        while(index){
            s.push(index);
            // ans.push_back(index->val); //前序遍历
            index= index->left;
        }
        index = s.top();
        s.pop();
        ans.push_back(index->val);	// 中序遍历
        if(index != nullptr)
            index = index->right;
    }
    return ans;
}
```

难点在非递归用栈来模拟， 前序遍历只需要改一个位置即可



#### [590. N叉树的后序遍历](https://leetcode-cn.com/problems/n-ary-tree-postorder-traversal/)

> 给定一个 N 叉树，返回其节点值的*后序遍历*。

```c
vector<int> postorder(Node* root) {
    vector<int> a;
    if (!root) return a;
    stack <Node*> stk;
    stk.push(root);
    Node* top;
    vector <Node*>::iterator it;
    while (!stk.empty()){
        top = stk.top();
        a.push_back(top->val);
        stk.pop();
        for (it = top->children.begin(); it != top->children.end() ; it++){
            stk.push(*it);
        }
    }
    reverse(a.begin(), a.end());
    return a;
}
```





#### [107. 二叉树的层次遍历 II](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)

给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）



```c
vector<vector<int>> levelOrderBottom(TreeNode* root) {
    if(root == nullptr) return vvint{};
    vvint ans;   
    traver(vector<TreeNode*>{root},ans);
    reverse(ans.begin(),ans.end());
    return ans;
}
void traver(vector<TreeNode*> set, vvint& ans){
    if(set.size() == 0) return;
    vector<TreeNode*> tmp;
    vint tmp1;
    for(const auto& i : set){
        if(i->left) tmp.push_back(i->left);
        if(i->right) tmp.push_back(i->right);
        tmp1.push_back(i->val);
    }
    ans.push_back(tmp1);
    traver(tmp,ans);
}
```

打气题！