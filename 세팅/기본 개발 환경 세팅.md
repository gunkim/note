## .vimrc
~~~sh
" Basic Settings
set number                  " Show line numbers
set nocompatible            " Be iMproved, required
set title                   " Show title
set wmnu                    " Show possible completions during tab completion
set tabstop=4               " Set tab width to 4 spaces
set autoindent
set cindent

" Syntax Highlighting
if has("syntax")
	syntax on
endif

colorscheme jellybeans

" Vundle Configuration
filetype off                " Required for Vundle
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" Vundle Plugins
Plugin 'VundleVim/Vundle.vim'       " Let Vundle manage Vundle, required
Plugin 'vim-airline/vim-airline'    " Status/tabline
Plugin 'scrooloose/nerdtree'        " File system explorer
Plugin 'airblade/vim-gitgutter'     " Shows git diff in the sign column
Plugin 'scrooloose/syntastic'       " Syntax checking plugin
Plugin 'tpope/vim-fugitive'         " Git wrapper
Plugin 'frazrepo/vim-rainbow'
Plugin 'nathanaelkane/vim-indent-guides'
Plugin 'machakann/vim-highlightedyank'
Plugin 'itchyny/vim-cursorword'
Plugin 'bad-whitespace'

call vundle#end()
filetype plugin indent on   " Required for Vundle

" Key Mappings
" Toggle NERDTree
map nerd :NERDTreeToggle<CR>

" vim-rainbow
let g:rainbow_active = 1

" vim-indent-guides
let g:indent_guides_enable_on_vim_startup = 1
~~~
## .zshrc
~~~
# My Zsh Setting

if [[ -r "${XDG_CACHE_HOME:-$HOME/.cache}/p10k-instant-prompt-${(%):-%n}.zsh" ]]; then
  source "${XDG_CACHE_HOME:-$HOME/.cache}/p10k-instant-prompt-${(%):-%n}.zsh"
fi

export ZSH="$HOME/.oh-my-zsh"

ZSH_THEME="powerlevel10k/powerlevel10k"

plugins=(
	git
	autojump
	zsh-autosuggestions
	zsh-syntax-highlighting
)

source ~/powerlevel10k/powerlevel10k.zsh-theme
source $ZSH/oh-my-zsh.sh

# To customize prompt, run `p10k configure` or edit ~/.p10k.zsh.
[[ ! -f ~/.p10k.zsh ]] || source ~/.p10k.zsh

# ---------- 여기부턴 개인 세팅 --------------

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion

# jdk
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"

alias ch-java-11='export JAVA_HOME=$(/usr/libexec/java_home -v 11); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'
alias ch-java-17='export JAVA_HOME=$(/usr/libexec/java_home -v 17); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'
alias ch-java-21='export JAVA_HOME=$(/usr/libexec/java_home -v 21); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'

# brew
export PATH=/opt/homebrew/bin:$PATH

# ruby
export PATH={$Home}/.rbenv/bin:$PATH && \
eval "$(rbenv init -)"

# autosuggestions 커스텀 키 바인딩
bindkey '^ ' autosuggest-accept
~~~